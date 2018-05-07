<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xst="http://www.w3.org/1999/XSL/Transform">
    
    <xst:template match="/">
        <div class="col-lg-5 col-md-6 mb-4">
            <div class="card h-100">
                <div class="card-body">
                    <h4 class="card-title text-primary">
                        <xsl:value-of select="medium/label"/>
                    </h4>
                    <div>
                        <i class="text-muted">
                            <xsl:choose>
                                <xsl:when test="medium/type='DVD'">
                                    <span class="fas fa-dot-circle"/>
                                    DVD
                                </xsl:when>
                                <xsl:when test="medium/type='VHS'">
                                    <span class="fas fa-tape"/>
                                    VHS
                                </xsl:when>
                                <xsl:when test="medium/type='USB'">
                                    <span class="fab fa-usb"/>
                                    USB
                                </xsl:when>
                                <xsl:otherwise>
                                    <span class="fas fa-minus"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </i>
                        <!-- DVD: <span class="fas fa-dot-circle"></span> -->
                        <!-- VHS: <span class="fas fa-tape"></span> -->
                        <!-- USB: <span class="fab fa-usb"></span> -->
                        <!-- other: <span class="fas fa-minus"></span> -->
                    </div>
                    <xsl:if test="medium/content">
                        <xsl:apply-templates select="medium/content"/>
                    </xsl:if>
                    <xsl:if test="medium/genres">
                        <xsl:apply-templates select="medium/genres/genre"/>
                    </xsl:if>
                    <div class="card-text">
                        <p>
                            <xsl:apply-templates select="medium/properties"/>
                        </p>
                        <div>
                            <button type="button" class="btn btn-secondary btn-sm">
                                <span class="fas fa-th-list"/>
                                Change Category
                            </button>
                        </div>
                    </div>
                </div>
                <div class="card-footer">
                    <small class="text-muted">
                        <xsl:value-of select="id"/>
                    </small>
                </div>
            </div>
        </div>
    </xst:template>
    
    <xsl:template match="genre">
        <p>
            <span class="badge badge-pill badge-secondary">
                <xsl:value-of select="@genre"/>
            </span>
        </p>
    </xsl:template>

    <xsl:template match="properties">
        <h5>Properties:</h5>
        <table class="table table-sm table-bordered">
            <xsl:for-each select="*">
                <tr>
                    <td>
                        <xsl:value-of select="name()"/>
                    </td>
                    <td>
                        <xsl:value-of select="current()"/>
                    </td>
                </tr>
            </xsl:for-each>

        </table>
    </xsl:template>

    <xsl:template match="content">
        <p>
            <h5>Entries:</h5>
            <ul class="list-group">
                <xsl:for-each select="entry">
                    <li class="list-group-item">
                        <xsl:value-of select="current()"/>
                    </li>
                </xsl:for-each>
            </ul>
        </p>
    </xsl:template>

</xsl:stylesheet>