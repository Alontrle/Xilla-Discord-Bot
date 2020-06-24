package net.xilla.discordcore.staff.group;

import com.tobiassteely.tobiasapi.api.manager.ManagerCache;
import com.tobiassteely.tobiasapi.api.manager.ManagerObject;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.config.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.xilla.discordcore.DiscordCore;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class GroupManager extends ManagerParent {

    public GroupManager() {
        super(true);
    }

    @Override
    public void reload() {
        super.reload();

        addCache("id", new ManagerCache());

        Config config = DiscordCore.getInstance().getTobiasAPI().getConfigManager().getConfig("staff/groups.json");
        JSONObject json = config.toJson();
        for(Object key : json.keySet()) {
            Group staff = new Group((Map<String, Object>)config.get(key.toString()));
            addGroup(staff);
        }
    }

    public void save() {
        Config config = DiscordCore.getInstance().getTobiasAPI().getConfigManager().getConfig("staff/groups.json");
        for(ManagerObject object : getList()) {
            Group staff = (Group)object;
            config.toJson().put(staff.getKey(), staff.toJson());
        }
        config.save();
    }

    public ArrayList<Group> getStaffByUserId(Guild guild, String id) {
        ArrayList<Group> staffList = new ArrayList<>();
        for(Object object : getList()) {
            Group staff = (Group)object;
            if(staff.isMember(guild, id)) {
                staffList.add(staff);
            }
        }
        return staffList;
    }

    public boolean hasPermission(Guild guild, User user, int level) {
        if(level == 0)
            return true;

        ArrayList<Group> staffList = getStaffByUserId(guild, user.getId());
        for(Group staff : staffList)
            if(staff.getLevel() >= level)
                return true;

        return false;
    }

    public Group getGroup(String name) {
        return (Group)getObjectWithKey(name);
    }

    public Group getGroupByID(String id) {
        return (Group)getCache("id").getObject(id);
    }

    public void addGroup(Group staff) {
        super.addObject(staff);

        getCache("id").putObject(staff.getGroupID(), staff);
    }
    
}
