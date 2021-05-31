import ASTNodes.Node;
import LuaVM.LuaChunk;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.cli.*;


import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Options options = new Options();

        options.addRequiredOption("i", "input", true, "The Luraph file to process.");
        options.addOption("p", false, "Print the optimized VM.");
        options.addOption("c", false, "Copy the optimized VM to clipboard.");
        options.addOption("s", false, "Display the AST of the Luraph VM");
        options.addOption("b", false, "Print devirtualized luac.");
        options.addOption("o", true, "Save devirtualized luac to given output file.");

        CommandLineParser cmdLineParser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = cmdLineParser.parse(options, args);
        }
        catch (ParseException ex) {
            System.out.println(ex.getMessage());
            new HelpFormatter().printHelp("LuraphDevirtualizer", options);
            System.exit(0);
        }

        String fileName = cmd.getOptionValue("i");

        // generate parse tree
        LuaLexer lexer = new LuaLexer(CharStreams.fromFileName(fileName));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LuaParser parser = new LuaParser(tokens);
        LuaParser.ChunkContext parseTree = parser.chunk();

        // generate AbstractSyntaxTree
        Node root = new BuildASTVisitor().visitChunk(parseTree);

        // optimize AST
        ASTOptimizerMgr optMgr = new ASTOptimizerMgr(root);
        optMgr.addOptimizer(new ASTConstantFolder());
        optMgr.addOptimizer(new ASTConstantPropagator());
        optMgr.optimize();

        // rename AST
        ASTOptimizerMgr renameMgr = new ASTOptimizerMgr(root);
        renameMgr.addRenamer(new ASTBasicRenamer());
        renameMgr.optimize();

        // devirtualize
        LuraphDevirtualizer devirtualizer = new LuraphDevirtualizer(root);
        LuaChunk chunk = devirtualizer.process();

        new LuaChunkOptimizer().optimize(chunk);

        chunk = LuaChunkOptimizer.removeClosureAntiSymbExecTrick(chunk);

        if (cmd.hasOption("p") || cmd.hasOption("c")) {
            String source = new ASTSourceGenerator(root).generate();

            if (cmd.hasOption("p")) {
                System.out.println(source);
            }

            if (cmd.hasOption("c")) {
                ClipboardUtils.set(source);
            }
        }

        if (cmd.hasOption("s")) {
            ASTTree tree = ASTTreeBuilder.create(root);
            ASTTreeViewer viewer = new ASTTreeViewer(tree);
            viewer.open();
        }

        if (cmd.hasOption("b")) {
            chunk.print();
        }

        if (cmd.hasOption("o")) {
            String outputFileName = cmd.getOptionValue("o");

            File output = new File(outputFileName);

            if (output.exists()) {
                System.out.println("ERROR: Output file already exists.");
                System.exit(1);
            }

            if (!output.createNewFile()) {
                System.out.println("ERROR: Could not create output file.");
                System.exit(1);
            }

            try {
                LuacGenerator generator = new LuacGenerator();
                generator.write(new FileOutputStream(outputFileName), chunk);
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
                System.exit(1);
            }

            System.out.println("Written file.");
        }
    }
}
