<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="/categories">
        <table class="table">
            <tr>
                <th>Category name</th>
                <th>Media count</th>
                <th>Delete category</th>
            </tr>
            <xsl:apply-templates select="category"/>
        </table>
    </xsl:template>

    <xsl:template match="category">
        <tr>
            <td><xsl:value-of select="name"/></td>
            <td><xsl:value-of select="count"/></td>
            <xsl:choose>
                <xsl:when test="count &gt; 0">
                    <td><a href="/delete_category?id={name}" role="button" class="btn btn-danger disabled">
                        <span class="fas fa-times"/> Delete</a></td>
                </xsl:when>
                <xsl:otherwise>
                    <td><a href="/delete_category?id={name}" role="button" class="btn btn-danger enabled">
                        <span class="fas fa-times"/> Delete</a></td>
                </xsl:otherwise>
            </xsl:choose>
        </tr>
    </xsl:template>

</xsl:stylesheet>