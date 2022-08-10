package com.kcn.data.InF;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.io.*;
import java.nio.file.Path;

public interface CommandMethod {

    Path path = FabricLoader.getInstance().getGameDir();
    String rule = """
            服务器规则:
            §e1.严禁任何人以任何名义拿取他人的一切物品,发现将永远进入服务器黑名单
            2.严禁任何人以任何名义摧毁他人的建筑,装置(除服主的特殊命令),发现将永远进入服务器黑名单
            3.严禁随意攻击任何玩家,发现将加入黑名单一周
            4.严禁使用外挂,宏等任何辅助作弊软件或设备,发现将永远进入服务器黑名单
            5.严禁发表任何不当言论(有关政治,色情,暴力,血腥,恐怖等),发现将将加入黑名单一月
            6.团结合作,有能力帮忙为服务器建设就尽量帮,项目结束后将计入功勋榜中""";

    String command = """
            服务器命令:
            注:红色指令仅管理员可用
            §b1.information
                rule 服务器规则
                sign 服务器公告
                command 服务器指令
                characteristic 服务器特性
            2.point
                set 创建传送点
                goto 前往传送点
                remove 移除传送点
                death 前往最近的死亡地点
            3.death 自杀
            4.tpa <targets> 传送到某一玩家处
            ----------------------------    
            1.§ccreeperExplosion§b
                open 爬行者爆炸破坏方块
                close 爬行者爆炸不破坏方块
            2.§cwitherBreak§b
                open 凋灵破坏方块
                close 凋灵不破坏方块
            3.§cgetUUID <targets>§b 获得某一玩家的UUID
            4.§cgetPos <targets>§b 获得某一玩家位于的维度,群系,坐标""";

    String characteristic = """
            服务器特性:
            §a1.本服大部分时间取消了苦力怕爆炸破坏方块的事件(在苦力怕爆炸时,玩家仍会受到爆炸伤害)
            2.本服内所有种子(小麦种子,南瓜种子,西瓜种子,甜菜种子)都可以食用,可以恢复1点饱食度,但有30%的概率获得2级的反胃效果15秒,可通过在烟熏炉内烘干去除毒性
            3.本服内所有生肉食用后都会获得10秒3级的中毒效果与10秒3级的虚弱效果,所有生鱼食用后会获得与食用生肉后一样的效果,但会附加10秒3级的反胃效果
            4.玩家在落到树叶上时有几率不受到摔落伤害
            5.储液罐在被破坏后,无论里面是否装着液体,都会回到空置状态
            §e注:本服所有§c新增§e合成表均在破坏一个木头后可以获得""";


    static void creeperExplosion(boolean explosion) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(new FileReader(path + "KCN\\data.json"));
        object.addProperty("creeperExplosion", explosion);
        String s = object.toString();
        BufferedWriter bw = new BufferedWriter(new FileWriter(path + "KCN\\data.json"));
        bw.write(s);
        bw.close();
    }

    static void witherBreak(boolean canBreak) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(new FileReader(path + "KCN\\data.json"));
        object.addProperty("witherBreak", canBreak);
        String s = object.toString();
        BufferedWriter bw = new BufferedWriter(new FileWriter(path + "KCN\\data.json"));
        bw.write(s);
        bw.close();
    }

    static String sign() throws Exception {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(path + "KCN\\sign.txt"), "UTF-8");
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        String text = new String(sb.toString());
        reader.close();
        return text;
    }

    static void file() throws Exception {
        File f = new File(path + "KCN\\Position");
        File f1 = new File(path + "KCN\\sign.txt");
        File f2 = new File(path + "KCN\\recipe");
        File dataJson = new File(path + "KCN\\data.json");
        File death = new File(path + "KCN\\death.json");
        if (!f.isDirectory()) {
            f.mkdirs();
            File playerJson = new File(path + "KCN\\Position\\player.json");
            playerJson.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(path + "KCN\\Position\\player.json"));
            bw.write("{}");
            bw.close();
            //----------------------------------------------------------------------------------------------------
            File positionJson = new File(path + "KCN\\Position\\position.json");
            positionJson.createNewFile();
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(path + "KCN\\Position\\position.json"));
            bw1.write("{}");
            bw1.close();
        }
        if (!f2.isDirectory()) {
            f2.mkdirs();
            File recipe1 = new File(path + "KCN\\recipe\\change.json");
            recipe1.createNewFile();
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(path + "KCN\\recipe\\change.json"));
            bw1.write("{}");
            bw1.close();
        }
        if (!f1.isFile()) {
            f1.createNewFile();
        }
        if (!dataJson.isFile()) {
            dataJson.createNewFile();
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(path + "KCN\\data.json"));
            bw2.write("{}");
            bw2.close();
        }
        if (!death.isFile()) {
            death.createNewFile();
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(path + "KCN\\death.json"));
            bw2.write("{}");
            bw2.close();
        }
    }

    static void removeFile(ServerPlayerEntity player) throws Exception {
        String uuid = String.valueOf(player.getUuid());
        JsonParser parser = new JsonParser();
        JsonObject playerObject = (JsonObject) parser.parse(new FileReader(path + "KCN\\Position\\player.json"));
        JsonObject positionObject = (JsonObject) parser.parse(new FileReader(path + "KCN\\Position\\position.json"));
        //创建player数组
        JsonArray playerArray = playerObject.getAsJsonArray("player");
        int i;
        boolean b = true;
        for (i = 0; i < playerArray.size(); i++) {
            if (uuid.equals(playerArray.get(i).getAsString())) {
                playerArray.remove(i);
                playerObject.add("player", playerArray);
                positionObject.remove(uuid);
                String s = playerObject.toString();
                String s1 = positionObject.toString();
                BufferedWriter bw = new BufferedWriter(new FileWriter(path + "KCN\\Position\\player.json"));
                bw.write(s);
                bw.close();
                BufferedWriter bw1 = new BufferedWriter(new FileWriter(path + "KCN\\Position\\position.json"));
                bw1.write(s1);
                bw1.close();
                String me = "§b已移除传送点";
                player.sendMessage(Text.of(me), false);
                b = false;
                break;
            }
        }
        if (b) {
            String error = "§c你未设置传送点,无法移除";
            player.sendMessage(Text.of(error), false);
        }
    }

    static void createFile(double x, double y, double z, ServerPlayerEntity sp) throws IOException {
        String uuid = String.valueOf(sp.getUuid());
        JsonParser parser = new JsonParser();
        JsonObject playerObject = (JsonObject) parser.parse(new FileReader(path + "KCN\\Position\\player.json"));
        JsonObject positionObject = (JsonObject) parser.parse(new FileReader(path + "KCN\\Position\\position.json"));
        //----------------------------------------------------------------------------------------------------
        JsonArray playerArray = playerObject.getAsJsonArray("player");
        BufferedWriter bw = new BufferedWriter(new FileWriter(path + "KCN\\Position\\player.json"));
        playerArray.add(uuid);
        playerObject.add("player", playerArray);
        String s = playerObject.toString();
        bw.write(s);
        bw.close();
        //----------------------------------------------------------------------------------------------------
        JsonObject newObject = new JsonObject();
        BufferedWriter bw1 = new BufferedWriter(new FileWriter(path + "KCN\\Position\\position.json"));
        newObject.addProperty("x", x);
        newObject.addProperty("y", y);
        newObject.addProperty("z", z);
        positionObject.add(uuid, newObject);
        String s1 = positionObject.toString();
        bw1.write(s1);
        bw1.close();
    }

    static void teleport(ServerPlayerEntity player) throws IOException {
        String uuid = String.valueOf(player.getUuid()); //获得玩家的uuid
        JsonParser parser = new JsonParser(); //创建json读取器
        //读取player.json文件
        JsonObject playerObject = (JsonObject) parser.parse(new FileReader(path + "KCN\\Position\\player.json"));
        //读取position.json文件
        JsonObject positionObject = (JsonObject) parser.parse(new FileReader(path + "KCN\\Position\\position.json"));
        //----------------------------------------------------------------------------------------------------
        //创建player数组
        JsonArray playerArray = playerObject.getAsJsonArray("player");
        int i;
        boolean b = true;
        for (i = 0; i < playerArray.size(); i++) {
            if (uuid.equals(playerArray.get(i).getAsString())) {
                //获取x,y,z坐标并传送
                JsonObject getPosition = positionObject.get(uuid).getAsJsonObject();
                double x = getPosition.get("x").getAsDouble();
                double y = getPosition.get("y").getAsDouble();
                double z = getPosition.get("z").getAsDouble();
                player.teleport(player.getWorld(), x, y, z, 0, 0);
                String me = "§b已传送至坐标§ax=" + x + "  y=" + y + "  z=" + z;
                player.sendMessage(Text.of(me), false);
                b = false;
                break;
            }
        }
        if (b) {
            String error = "§c你未设置传送点,无法传送";
            player.sendMessage(Text.of(error), false);
        }
    }

    static void death(ServerPlayerEntity player) throws Exception {
        String uuid = String.valueOf(player.getUuid());
        Path path = FabricLoader.getInstance().getGameDir();
        JsonParser parser = new JsonParser();
        JsonObject playerObject = (JsonObject) parser.parse(new FileReader(path + "KCN\\death.json"));
        if (playerObject.get(uuid) != null) {
            JsonObject object = playerObject.get(uuid).getAsJsonObject();
            double x = object.get("x").getAsDouble();
            double y = object.get("y").getAsDouble();
            double z = object.get("z").getAsDouble();
            player.teleport(player.getWorld(), x, y, z, 0, 0);
        } else {
            String error = "§c你未曾死亡过,无法传送";
            player.sendMessage(Text.of(error), false);
        }
    }
}
