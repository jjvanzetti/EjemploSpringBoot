Poio:
	clave privada: pksb
	alias: SAN-BORG
	comando para generar el certificado: openssl req -new -key pksb -subj "/C=AR/O=SAN-BORG INTERNACIONAL S.A./CN=SAN-BORG/serialNumber=CUIT 30712242341" -out sanborgfe

keystore: SAN-BORG.p12
keystore-signer: SAN-BORG
keystore-password: pksb

