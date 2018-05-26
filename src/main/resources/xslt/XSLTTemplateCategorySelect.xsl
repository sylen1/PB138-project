<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <select name="category">
            <xsl:apply-templates/>
        </select>
    </xsl:template>

    <xsl:template match="category">
        <otion>
            <xsl:value-of select="."/>
        </otion>
    </xsl:template>


</xsl:stylesheet>