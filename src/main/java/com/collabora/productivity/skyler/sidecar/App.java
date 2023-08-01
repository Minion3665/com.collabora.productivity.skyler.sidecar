package com.collabora.productivity.skyler.sidecar;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.text.XText;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;

public class App {

    public static void main(String[] args) throws Throwable {
        System.out.println("Hello world!");
        // get the remote office component context
        com.sun.star.uno.XComponentContext xContext;
        try {
            xContext = com.sun.star.comp.helper.Bootstrap.bootstrapWebsocketConnection(false, "localhost", 9981, "cool/http%3A%2F%2Flocalhost%3A9980%2Fwopi%2Ffiles%2Fhome%2Fskyler%2FCode%2Fcollabora%2Fonline%2Ftest%2Fdata%2Fhello-world.odt%3Faccess_token%3Dtest%26access_token_ttl%3D0/ws");
        } catch (BootstrapException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());

            e.printStackTrace();

            if (e.getTargetException().getCause() != null) {
                throw e.getTargetException().getCause();
            }

            throw e.getTargetException();
        }

        System.out.println("Connected to a running office ...");

        com.sun.star.lang.XMultiComponentFactory xMCF =
            xContext.getServiceManager();

        if (xMCF == null) {
            System.out.println( "remote ServiceManager is not available" );
            System.exit(1);
        }

        System.out.println("Got the remote ServiceManager");

        String loadUrl = "private:factory/swriter";
        Object desktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext);
        XComponentLoader xComponentLoader = (XComponentLoader) UnoRuntime.queryInterface(
            XComponentLoader.class, desktop);
        PropertyValue[] loadProps = new PropertyValue[0];


        XComponent xWriterComponent = xComponentLoader.loadComponentFromURL(loadUrl, "_blank", 0, loadProps);
        XTextDocument xTextDocument = (XTextDocument)UnoRuntime.queryInterface(
            XTextDocument.class, xWriterComponent);
        XText xText = xTextDocument.getText();

        int x = 0;
        XTextCursor cursor = xText.createTextCursor();
        String lipsum = "This document was created with URP over websockets!";

        xText.insertString(cursor, lipsum, false);

        XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, xTextDocument);
        xStorable.storeToURL("file:///tmp/hello-urpws.odt", new PropertyValue[0]);
    }
}

