import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.printer.PrettyPrinter;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;

import java.io.IOException;
import java.nio.file.Path;


final class AnnotationRemover {

    /**
     * @see AnnotationRemoverVisitor
     */
    String deleteFromAnnotation(boolean lexical, Path filePath) throws IOException {
        CompilationUnit compilationUnit = readFromFileWithLexicalPreservingPrinter(filePath);
        GenericVisitor visitor = new AnnotationRemoverVisitor();
        visitor.visit(compilationUnit, null);
        return serialize(lexical, compilationUnit);
    }

    private String serialize(boolean lexical, CompilationUnit compilationUnit) {
        String print;
        if (lexical)
        {
            System.out.println("Using lexical preserving printer:");
            print = LexicalPreservingPrinter.print(compilationUnit);
        }
        else
        {
            System.out.println("Using pretty print:");
            PrettyPrinterConfiguration conf = new PrettyPrinterConfiguration();
            PrettyPrinter printer = new PrettyPrinter(conf);
            print = printer.print(compilationUnit);
        }
        System.out.println(print);
        System.out.println();
        return print;
    }

    private CompilationUnit readFromFileWithLexicalPreservingPrinter(Path fileName) throws IOException {
        CompilationUnit compilationUnit = JavaParser.parse(fileName.toFile());
        LexicalPreservingPrinter.setup(compilationUnit);
        return compilationUnit;
    }
}