<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:template match="/">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="medium">
        <div class="col-lg-5 col-md-6 mb-4">
            <div class="card h-100">
                <div class="card-body">
                    <h4 class="card-title text-primary">
                        <xsl:value-of select="label" />
                    </h4>
                    <div>
                        <xsl:apply-templates select="type"/>
                    </div>
                    <xsl:apply-templates select="genres"/>

                    <xsl:apply-templates select="content"/>

                    <div class="card-text">
                        <xsl:apply-templates select="properties" />
                    </div>
                    <div>
                        <button type="button" class="btn btn-secondary btn-sm"><span class="fas fa-th-list"></span> Change Category</button>
                    </div>
                </div>
                <div class="card-footer">
                    <xsl:apply-templates select="id"/>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="genres">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="genre">
        <span class="badge badge-pill badge-secondary"><xsl:value-of select="."/></span>
    </xsl:template>

    <xsl:template match="type">
        <xsl:choose>
            <xsl:when test="text() = 'DVD'">
                <i class="text-muted"><span class="fas fa-dot-circle"></span> DVD</i>
            </xsl:when>
            <xsl:when test="text() = 'VHS'">
                <i class="text-muted"><span class="fas fa-tape"></span> VHS</i>
            </xsl:when>
            <xsl:when test="text() = 'USB'">
                <i class="text-muted"><span class="fab fa-usb"></span> USB</i>
            </xsl:when>
            <xsl:otherwise>
                <i class="text-muted"><span class="fas fa-minus"></span> other</i>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="content">
        <p>
            <h5>Entries:</h5>
            <ul class="list-group">
                <xsl:apply-templates select="entry"/>
            </ul>
        </p>
    </xsl:template>

    <xsl:template match="entry">
        <li class="list-group-item"><xsl:value-of select="."/></li>
    </xsl:template>

    <xsl:template match="properties">
        <p>
            <h5>Properties:</h5>
            <table class="table table-sm table-bordered">
                <tr>
                    <xsl:apply-templates mode="property"/>
                </tr>
            </table>
        </p>
    </xsl:template>

    <xsl:template match="*" mode="property">
        <tr>
            <td><xsl:value-of select="name(.)"/></td>
            <td><xsl:value-of select="."/></td>
        </tr>
    </xsl:template>

    <xsl:template match="id">
        <small class="text-muted">Record id: <xsl:value-of select="."/></small>
    </xsl:template>

</xsl:stylesheet>