<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    <xsl:template match="/students">
        <html lang="en">
            <head>
                <meta charset="UTF-8"/>
                <title>Student academic report</title>
                <style>
                    body { font: 16px/1.6 Arial, sans-serif; color: #203047; background: #f3f6fa; margin: 40px auto; max-width: 960px; padding: 0 24px; }
                    h1 { color: #173d67; } article { background: white; padding: 24px; margin: 24px 0; border-top: 4px solid #267d91; }
                    h2 { margin-top: 0; } table { width: 100%; border-collapse: collapse; }
                    th, td { text-align: left; padding: 10px; border-bottom: 1px solid #dce4ed; }
                    th { background: #e8eff7; } .meta { color: #52647a; } caption { text-align: left; font-weight: bold; padding: 12px 0; }
                </style>
            </head>
            <body>
                <h1>Student academic report</h1>
                <p><xsl:value-of select="count(student)"/> students | Enrollment and subject results</p>
                <xsl:for-each select="student">
                    <article>
                        <h2><xsl:value-of select="name"/></h2>
                        <p class="meta">ID: <xsl:value-of select="id"/> | Age: <xsl:value-of select="age"/> | Group: <xsl:value-of select="groupCode"/> | GPA: <xsl:value-of select="gpa"/> | Status:
                            <xsl:choose><xsl:when test="active='true'">Active</xsl:when><xsl:otherwise>Inactive</xsl:otherwise></xsl:choose>
                        </p>
                        <table>
                            <caption>Subjects</caption>
                            <thead><tr><th scope="col">Subject</th><th scope="col">Credits</th><th scope="col">Score / 100</th></tr></thead>
                            <tbody><xsl:for-each select="subjects/subject"><tr>
                                <td><xsl:value-of select="name"/></td><td><xsl:value-of select="credits"/></td><td><xsl:value-of select="score"/></td>
                            </tr></xsl:for-each></tbody>
                        </table>
                    </article>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
