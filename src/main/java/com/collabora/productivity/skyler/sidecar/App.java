package com.collabora.productivity.skyler.sidecar;

import com.sun.star.beans.PropertyValue;
import com.sun.star.comp.helper.BootstrapException;
import com.sun.star.frame.XComponentLoader;
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
        String lipsum = "\n" + //
                "\n" + //
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ac consequat justo, non fermentum felis. Suspendisse rutrum dui suscipit blandit egestas. Sed ultrices tortor dolor, eu sodales ex volutpat non. Sed faucibus nisl vitae tellus volutpat, et efficitur massa vulputate. Phasellus sed vehicula est, eu iaculis sapien. Phasellus id accumsan nisl, vel lacinia odio. Curabitur enim dui, bibendum a bibendum mattis, interdum eu turpis. Etiam nec lectus egestas libero consequat rhoncus eu vel eros. Duis in fringilla justo, a euismod felis. Donec eget sodales nibh. Suspendisse vestibulum orci lectus. Cras pellentesque feugiat massa iaculis vestibulum.\n" + //
                "\n" + //
                "Fusce finibus tempus risus, et facilisis quam ullamcorper sit amet. Nullam ac massa eget mi dignissim fermentum in congue erat. Aenean ornare, neque non luctus consectetur, diam dui rhoncus est, fringilla cursus ante elit ut ligula. Sed pellentesque ornare arcu, nec luctus sapien imperdiet a. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus efficitur mattis sapien, sit amet lacinia diam pulvinar eget. Sed lacinia in eros lobortis vestibulum. Nam ullamcorper vulputate lectus in porttitor. In turpis orci, sagittis eget lacus in, viverra suscipit arcu. Praesent diam lorem, faucibus quis nisl a, maximus luctus nisl.\n" + //
                "\n" + //
                "Nam consequat interdum tincidunt. Vestibulum id velit a sapien posuere sodales dapibus eu orci. Aliquam vitae nulla facilisis, ultrices neque ut, elementum urna. Mauris consequat leo at neque porta porta. Nam diam erat, tincidunt non blandit iaculis, auctor ut quam. Vestibulum vel mi vel lorem vulputate dictum at id urna. In eu venenatis neque. Donec eget placerat lacus, at luctus lacus. Integer blandit volutpat ligula, vitae lobortis est malesuada ut.\n" + //
                "\n" + //
                "Nam ac laoreet dui. Etiam ut turpis maximus massa condimentum eleifend ut ut nisi. Nam et sodales leo. Cras sollicitudin mauris et suscipit tempus. In commodo faucibus mauris at aliquet. Pellentesque at consequat lorem. In interdum diam sit amet ante. ";
        while (true) {
            for (char character : lipsum.toCharArray()) {
                Thread.sleep(10);
                xText.insertString(cursor, Character.toString(character), false);
                cursor.gotoEnd(false);
            }
        }
    }
}

