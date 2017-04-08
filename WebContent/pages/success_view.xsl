<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<header>
				<h1><xsl:value-of select="view/header/title"></xsl:value-of></h1>
			</header>
			<body>
			<form>
				<xsl:attribute name="name">
					<xsl:value-of select="view/body/form/name"></xsl:value-of>
				</xsl:attribute>
				<xsl:attribute name="action">
					<xsl:value-of select="view/body/form/action"></xsl:value-of>
				</xsl:attribute>
				<xsl:attribute name="method">
					<xsl:value-of select="view/body/form/method"></xsl:value-of>
				</xsl:attribute>
			<xsl:for-each select="view/body/form/textView">
				<xsl:value-of select="label"></xsl:value-of>
				<input type="text" >
					<xsl:attribute name="name">
						<xsl:value-of select="name"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="value"></xsl:value-of>
					</xsl:attribute>
				</input>
				<br/>
			</xsl:for-each>
			
			<xsl:for-each select="view/body/form/buttonView">
				<!-- <input type="button">
					<xsl:attribute name="name">
						<xsl:value-of select="name"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="label">
						<xsl:value-of select="label"></xsl:value-of>
					</xsl:attribute>
				</input> -->
				<button>
					<xsl:attribute name="name">
						<xsl:value-of select="name"></xsl:value-of>
					</xsl:attribute>
					<label><xsl:value-of select="label"></xsl:value-of></label>
				</button>
			</xsl:for-each>
			</form>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>