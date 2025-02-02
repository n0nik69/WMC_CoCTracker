package net.htlgkr.lugern.coctracker.einModel.einClan;

import java.util.ArrayList;
import java.util.List;

public class ClanMemberList {
    List<ClanMember> clanMember = new ArrayList<>();

    public List<ClanMember> getClanMemberList() {
        return clanMember;
    }

    public void setClanMemberList(List<ClanMember> clanMemberList) {
        this.clanMember = clanMemberList;
    }
}
