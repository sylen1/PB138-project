<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:template match="/">
        <div class="list-group">
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <xsl:template match="category">
        <a href="/?category={.}" class="list-group-item"><xsl:value-of select="."/></a>
    </xsl:template>

</xsl:stylesheet>
