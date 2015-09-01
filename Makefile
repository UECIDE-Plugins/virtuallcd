build:
	ant

install:
	mkdir ${DESTDIR}/plugins
	install -m 755 VirtualLCD.jar ${DESTDIR}/plugins/

clean:
	ant clean
