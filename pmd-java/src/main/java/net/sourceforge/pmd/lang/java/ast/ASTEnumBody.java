/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.ast;

import net.sourceforge.pmd.lang.ast.NodeStream;

/**
 * Body of an {@linkplain ASTEnumDeclaration enum declaration}.
 *
 * <pre class="grammar">
 *
 * EnumBody ::= "{"
 *              [ {@link ASTEnumConstant EnumConstant} ( "," ( {@link ASTEnumConstant EnumConstant} )* ]
 *              [ "," ]
 *              [ ";" ( {@link ASTBodyDeclaration ClassOrInterfaceBodyDeclaration} )* ]
 *              "}"
 *
 * </pre>
 *
 *
 */
public final class ASTEnumBody extends AbstractJavaNode implements ASTTypeBody {

    private boolean trailingComma;
    private boolean separatorSemi;

    ASTEnumBody(int id) {
        super(id);
    }

    @Override
    public Object jjtAccept(JavaParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }


    @Override
    public <T> void jjtAccept(SideEffectingVisitor<T> visitor, T data) {
        visitor.visit(this, data);
    }

    @Override
    public NodeStream<ASTEnumConstant> getEnumConstants() {
        return children(ASTEnumConstant.class);
    }

    void setTrailingComma() {
        this.trailingComma = true;
    }

    void setSeparatorSemi() {
        this.separatorSemi = true;
    }

    /**
     * Returns true if the last enum constant has a trailing comma.
     * For example:
     * <pre>{@code
     * enum Foo { A, B, C, }
     * enum Bar { , }
     * }</pre>
     */
    public boolean hasTrailingComma() {
        return trailingComma;
    }

    /**
     * Returns true if the last enum constant has a trailing semi-colon.
     * This semi is not optional when the enum has other members.
     * For example:
     * <pre>{@code
     * enum Foo {
     *   A(2);
     *
     *   Foo(int i) {...}
     * }
     *
     * enum Bar { A; }
     * enum Baz { ; }
     * }</pre>
     */
    public boolean hasSeparatorSemi() {
        return separatorSemi;
    }
}
