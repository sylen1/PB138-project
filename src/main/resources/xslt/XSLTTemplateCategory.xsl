<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <div class="col-lg-3">
            <h2 class="my-4">Categories</h2>
            <div class="list-group">
                <xsl:for-each select="collection/category">
                    <a href="#" class="list-group-item">
                        <xsl:value-of select="@name"/>
                    </a>
                </xsl:for-each>
            </div>
            <div class="mt-4">
                <button type="button" class="btn btn-primary"><span class="fas fa-th-list"></span> Manage Categories</button>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>