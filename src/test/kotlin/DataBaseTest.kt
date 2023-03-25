import com.github.zzwtsy.utils.SqliteUtils.createTable
import com.github.zzwtsy.utils.SqliteUtils.executeQuery
import com.github.zzwtsy.utils.SqliteUtils.executeUpdate

fun main() {
    val url = "jdbc:sqlite:test.db"

//    characters 表：用于存储人物信息，包括 ID 和名称。
//    character_aliases 表：用于存储人物的别名，其中每个别名都与一个人物 ID 相关联。
//
//    CREATE TABLE characters (
//        id INTEGER PRIMARY KEY,
//        name TEXT NOT NULL
//    );
//
//    CREATE TABLE character_aliases (
//        alias TEXT NOT NULL,
//        character_id INTEGER NOT NULL,
//        FOREIGN KEY (character_id) REFERENCES characters (id)
//    );
//    characters 表包含两个列：
//
//    id 列：存储人物的 ID。
//    name 列：存储人物的名称。
//
//    character_aliases 表也包含两个列：
//
//    alias 列：存储人物的别名。
//    character_id 列：存储与该别名相关联的人物 ID。

    // 创建 characters 表
//    url.createTable("characters") {
//        column("id", "INTEGER") {
//            primaryKey()
//        }
//        column("name", "TEXT") {
//            notNull()
//        }
//    }
//
//    // 创建 character_aliases 表
//    url.createTable("character_aliases") {
//        column("alias", "TEXT") {
//            notNull()
//        }
//        column("character_id", "INTEGER") {
//            notNull()
//            foreignKey("character_id", "characters", "id")
//        }
//    }
//
//
//    // 插入数据
//    url.executeUpdate("INSERT INTO characters (id, name) VALUES (20000000, '主角')")
//    url.executeUpdate("INSERT INTO characters (id, name) VALUES (10000002, '神里绫华')")
//    url.executeUpdate("INSERT INTO characters (id, name) VALUES (10000003, '琴')")
//    url.executeUpdate("INSERT INTO character_aliases (alias, character_id) VALUES ('旅行者', 20000000)")
//    url.executeUpdate("INSERT INTO character_aliases (alias, character_id) VALUES ('卑鄙的外乡人', 20000000)")


    // 查询数据
    url.executeQuery("SELECT * FROM characters") {
        while (this.next()) {
            val id = this.getInt("id")
            val name = this.getString("name")

            println("$id\t$name\t")
        }
    }
    url.executeQuery("SELECT * FROM character_aliases") {
        while (this.next()) {
            val alias = this.getString("alias")
            val characterId = this.getInt("character_id")

            println("$characterId\t$alias\t")
        }
    }
}