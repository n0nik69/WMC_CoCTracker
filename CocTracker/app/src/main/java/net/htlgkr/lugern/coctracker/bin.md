setIfExistsInt(member, "bestTrophies", player::setBestTrophies);
// setIfExistsInt(member, "warStars", player::setWarStars);
// setIfExistsInt(member, "attackWins", player::setAttackWins);
// setIfExistsInt(member, "defenseWins", player::setDefenseWins);
// setIfExistsInt(member, "townhallLevel", player::setTownHallLevel);
// setIfExistsInt(member, "townhallWeaponLevel", player::setTownHallWeaponLevel);
// setIfExistsInt(member, "builderhallLevel", player::setBuilderHallLevel);
// setIfExistsInt(member, "bestBuilderBaseTrophies", player::setBestBuilderBaseTrophies);
// setIfExistsString(member, "warPreference", player::setWarPreference);
// setIfExistsInt(member, "clanCapitalContributions", player::setClanCapitalContributions);

// List<PlayerItemLevel> troops = new ArrayList<>();
// setListIfExists(member, "troops", this::addToPlayerItemList, troops);
// player.setTroops(troops);
//
// List<PlayerItemLevel> heroes = new ArrayList<>();
// setListIfExists(member, "heroes", this::addToPlayerItemList, heroes);
// player.setHeroes(heroes);
//
// List<PlayerItemLevel> heroEquipments = new ArrayList<>();
// setListIfExists(member, "heroEquipments", this::addToPlayerItemList, heroEquipments);
// player.setHeroEquipments(heroEquipments);
//
// List<PlayerItemLevel> spells = new ArrayList<>();
// setListIfExists(member, "spells", this::addToPlayerItemList, spells);
// player.setSpells(spells);

private <T> void setListIfExists(JSONObject member, String key, BiConsumer<List<PlayerItemLevel>,
JSONObject> consumer, List<PlayerItemLevel> list) {
try {
if (member.has(key)) {
JSONArray array;
array = member.getJSONArray(key);
for (int i = 0; i < array.length(); i++) {
JSONObject object;
object = array.getJSONObject(i);
consumer.accept(list, object);
}
}
} catch (JSONException e) {
throw new RuntimeException(e);
}
}

    private void addToPlayerItemList(List<PlayerItemLevel> list, JSONObject object) {
        PlayerItemLevel item = new PlayerItemLevel();
        try {
            if (object.has("level")) item.setLevel(object.getInt("level"));
            if (object.has("name")) item.setName(object.getString("name"));
            if (object.has("maxLevel")) item.setMaxLevel(object.getInt("maxLevel"));
            if (object.has("village"))
                item.setVillage(Village.valueOf(object.getString("village")));
            if (object.has("superTroopIsActive"))
                item.setSuperTroopIsActive(object.getBoolean("superTroopIsActive"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        list.add(item);
    }