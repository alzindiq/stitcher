package io.stitcher.extras;

import com.hp.hpl.loom.adapter.hpcloud.item.ChefClientItem;
import com.hp.hpl.loom.adapter.hpcloud.item.ChefOrgItem;
import io.stitcher.StitchChecker;

public class ChefClientChefOrgStitchChecker implements StitchChecker<ChefClientItem, ChefOrgItem> {

    public double checkStitch(ChefClientItem baseElement, ChefOrgItem candidateElement) {
        String chefClientOrgGuid = (String) baseElement.getCore().getPayload().getAttribute("org_guid");
        String chefOrgGuid = (String) candidateElement.getCore().getPayload().getAttribute("guid");

        if (chefClientOrgGuid.equals(chefOrgGuid)) {
            return 1.0;
        } else {
            return 0.0;
        }
    }

}
