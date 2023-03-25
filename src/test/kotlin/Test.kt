import com.github.zzwtsy.utils.SqliteUtils
import com.github.zzwtsy.utils.SqliteUtils.createTable
import com.github.zzwtsy.utils.SqliteUtils.executeQuery

const val url = "jdbc:sqlite:test.db"

fun main() {

//    val text = File("src/main/resources/role/name.yml")
//    val yaml = Yaml()
//    val data = yaml.load<Map<Int, List<String>>>(text.reader())
//    val charactersList: MutableList<String> = mutableListOf()
//    val characterAliases: MutableList<String> = mutableListOf()

//    INSERT INTO characters (id, name) VALUES (20000000, '主角')
//    INSERT INTO character_aliases (alias, character_id) VALUES ('旅行者', 20000000)
//    data.forEach {
//        charactersList.add("INSERT INTO characters (id, name) VALUES (${it.key}, '${it.value[0]}')")
//        it.value.forEach { e ->
//            characterAliases.add("INSERT INTO character_aliases (alias, character_id) VALUES ('${e}', ${it.key})")
//        }
//    }

    val id = url.executeQuery("SELECT * FROM character_aliases WHERE alias = '万叶'") {
        var characterId = 0
        while (this.next()) {
            characterId = this.getInt("character_id")
        }
        characterId
    }

    println(id)

//    crateTable()
//
//    for (s in charactersList) {
//        url.executeUpdate(s)
//    }
//
//    for (alias in characterAliases) {
//        url.executeUpdate(alias)
//    }

}

fun listToFormatSql(list: List<String>): String {
    val sb = StringBuilder()
    for (s in list) {
        if (s == "[" || s == "]" || s == ",") continue
        sb.append(s).append("\n")
    }
    return sb.toString()
}


fun crateTable() {
    // 创建 characters 表
    url.createTable("characters") {
        column("id", "INTEGER") {
            primaryKey()
        }
        column("name", "TEXT") {
            notNull()
        }
    }

    // 创建 character_aliases 表
    url.createTable("character_aliases") {
        column("alias", "TEXT") {
            notNull()
        }
        column("character_id", "INTEGER") {
            notNull()
            foreignKey("character_id", "characters", "id")
        }
    }
}