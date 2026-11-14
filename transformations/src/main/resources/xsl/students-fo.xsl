<?xml version="1.0" encoding="UTF-8"?>
<!-- XSLT produces an XSL-FO document. Apache FOP renders that document to PDF. -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <xsl:output method="xml" encoding="UTF-8" indent="yes"/>
    <xsl:template match="/students">
        <fo:root font-family="Helvetica" font-size="10pt" color="#203047">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-width="210mm" page-height="297mm" margin="20mm">
                    <fo:region-body margin-bottom="12mm"/><fo:region-after extent="8mm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4">
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block font-size="9pt" text-align="right" color="#52647a">Academic report | Page <fo:page-number/></fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="24pt" color="#173d67" space-after="6mm">Student academic report</fo:block>
                    <fo:block space-after="10mm"><xsl:value-of select="count(student)"/> students | Enrollment and subject results</fo:block>
                    <xsl:for-each select="student">
                        <fo:block font-size="16pt" color="#173d67" space-before="7mm" space-after="3mm" keep-with-next="always"><xsl:value-of select="name"/></fo:block>
                        <fo:block space-after="2mm" keep-with-next="always">ID: <xsl:value-of select="id"/> | Age: <xsl:value-of select="age"/> | Group: <xsl:value-of select="groupCode"/> | GPA: <xsl:value-of select="gpa"/></fo:block>
                        <fo:block space-after="4mm" keep-with-next="always">Status: <xsl:choose><xsl:when test="active='true'">Active</xsl:when><xsl:otherwise>Inactive</xsl:otherwise></xsl:choose></fo:block>
                        <fo:table table-layout="fixed" width="100%" border-collapse="collapse">
                            <fo:table-column column-width="100mm"/><fo:table-column column-width="30mm"/><fo:table-column column-width="40mm"/>
                            <fo:table-header background-color="#e8eff7" font-weight="bold"><fo:table-row>
                                <fo:table-cell padding="3mm"><fo:block>Subject</fo:block></fo:table-cell>
                                <fo:table-cell padding="3mm"><fo:block>Credits</fo:block></fo:table-cell>
                                <fo:table-cell padding="3mm"><fo:block>Score / 100</fo:block></fo:table-cell>
                            </fo:table-row></fo:table-header>
                            <fo:table-body><xsl:for-each select="subjects/subject"><fo:table-row border-bottom="0.5pt solid #dce4ed">
                                <fo:table-cell padding="3mm"><fo:block><xsl:value-of select="name"/></fo:block></fo:table-cell>
                                <fo:table-cell padding="3mm"><fo:block><xsl:value-of select="credits"/></fo:block></fo:table-cell>
                                <fo:table-cell padding="3mm"><fo:block><xsl:value-of select="score"/></fo:block></fo:table-cell>
                            </fo:table-row></xsl:for-each></fo:table-body>
                        </fo:table>
                    </xsl:for-each>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
