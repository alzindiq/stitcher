package io.stitcher.extras;

import com.hp.hpl.loom.adapter.hpcloud.item.HostItem;
import com.hp.hpl.loom.adapter.hpcloud.item.VmItem;
import io.stitcher.StitchChecker;

public class VmHostStitchChecker implements StitchChecker<VmItem, HostItem> {

    public double checkStitch(VmItem baseElement, HostItem candidateElement) {
        String vmRefHost = (String) baseElement.getCore().getPayload().getAttribute("ref_host");
        String hostRecordId = candidateElement.getCore().getRecord_id();

        if (vmRefHost.equals(hostRecordId)) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

}
