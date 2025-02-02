package net.htlgkr.lugern.coctracker.models.clan;

import java.util.List;

public class ClanCapital {
    private int capitalHallLevel;
    private List<ClanDistrictData> clanDistrictDataList;

    public int getCapitalHallLevel() {
        return capitalHallLevel;
    }

    public void setCapitalHallLevel(int capitalHallLevel) {
        this.capitalHallLevel = capitalHallLevel;
    }

    public List<ClanDistrictData> getClanDistrictDataList() {
        return clanDistrictDataList;
    }

    public void setClanDistrictDataList(List<ClanDistrictData> clanDistrictDataList) {
        this.clanDistrictDataList = clanDistrictDataList;
    }
}
