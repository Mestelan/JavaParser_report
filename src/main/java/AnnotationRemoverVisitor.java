import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.Iterator;

/**
 * Given a string, we want to remove it from the annotation.
 */
final class AnnotationRemoverVisitor extends ModifierVisitor<Void> {
    public static final String REFERENCE_TO_BE_DELETED = "bad";

    public AnnotationExpr visit(final NormalAnnotationExpr annotation, final Void arg) {
        super.visit(annotation, arg);

        MemberValuePair mvp = annotation.getPairs().get(0);
        Expression value = mvp.getValue();
        if ((value instanceof ArrayInitializerExpr)) {
            NodeList<Expression> myElements = ((ArrayInitializerExpr) value).getValues();

            for (Iterator<Expression> iterator = myElements.iterator(); iterator.hasNext(); ) {
                Node elt = iterator.next();
                {
                    String nameAsString = ((StringLiteralExpr) elt).asString();
                    if (REFERENCE_TO_BE_DELETED.equals(nameAsString))
                        iterator.remove();
                }
            }
            if (myElements.size() == 0)
                return null;
            return annotation;
        }
        throw new IllegalStateException();
    }
}
