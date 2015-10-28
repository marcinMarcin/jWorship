package de.humatic.media.content.dsj;


import javax.media.Controller;
import javax.media.InternalErrorEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.protocol.DataSource;


class DSJRealizer extends Thread {
    private Handler handler;

    private DataSource dataSrc;

    public DSJRealizer(Handler h, DataSource ds, ThreadGroup tg) {
        super(tg, "DSJRealizer");

        this.handler = h;

        dataSrc = ds;

    }

    // this thread exists to transition from realizing to
    // realized state.

    public void run() {


        handler.setPreviousState(handler.currentState);
        handler.setCurrentState(Controller.Realizing);
        handler.setTargetState(Controller.Realized);

        try {

            ((de.humatic.media.protocol.dsj.DataSource) dataSrc).createFiltergraph();

        } catch (Exception ioe) {
            InternalErrorEvent uhoh;
            uhoh = new InternalErrorEvent(handler, ioe.toString());
            handler.addEvent(uhoh);
            return;
        }


        // update state to realize

        handler.setPreviousState(handler.currentState);
        handler.setCurrentState(Controller.Realized);
        handler.setTargetState(Controller.Realized);

        // notify listeners that realize is done.
        handler.realizer = null;
        handler.realizeCompleted = true;
        RealizeCompleteEvent rce = new RealizeCompleteEvent(handler,
                Controller.Unrealized,
                Controller.Realized,
                Controller.Realized);
        handler.addEvent(rce);

    }

}
