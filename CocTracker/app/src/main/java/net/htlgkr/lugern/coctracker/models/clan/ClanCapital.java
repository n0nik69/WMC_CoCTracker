package net.htlgkr.lugern.coctracker.models.clan;

import java.util.List;

public class ClanCapital {
    private int capitalHallLevel;
    private List<ClanDistrictData> clanDistrictDataList;

    public ClanCapital(int capitalHallLevel, List<ClanDistrictData> clanDistrictDataList) {
        this.capitalHallLevel = capitalHallLevel;
        this.clanDistrictDataList = clanDistrictDataList;
    }
}
