package com.github.zzwtsy.data.dataConfig

import kotlinx.serialization.SerialName
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * 角色名称别名
 * @author zzwtsy
 * @date 2023/03/24
 */
@ValueDescription("原神角色 id 与别名映射")
object RoleNameAlias : AutoSavePluginData("RoleNameAlise") {
    @SerialName("20000000")
    val traveler by value(
        listOf(
            "主角",
            "旅行者",
            "卑鄙的外乡人",
            "荣誉骑士",
            "爷",
            "风主",
            "岩主",
            "雷主",
            "草主",
            "履刑者",
            "抽卡不歪真君",
            "空",
            "男主",
            "男主角",
            "龙哥",
            "空哥",
            "男爷",
            "荧",
            "女主",
            "女主角",
            "莹",
            "萤",
            "黄毛阿姨",
            "荧妹",
            "女爷"
        )
    )

    @SerialName("10000002")
    val ayaka by value(
        listOf(
            "神里绫华",
            "Kamisato Ayaka",
            "Ayaka",
            "ayaka",
            "神里",
            "绫华",
            "神里凌华",
            "凌华",
            "白鹭公主",
            "神里大小姐",
            "小乌龟",
            "龟龟",
        )
    )

    @SerialName("10000003")
    val jean by value(
        listOf(
            "琴", "Jean", "jean", "团长", "代理团长", "琴团长", "蒲公英骑士", "琴·古恩希尔德", "古恩希尔德",
        )
    )

    @SerialName("10000006")
    val lisa by value(
        listOf(
            "丽莎", "Lisa", "lisa", "图书管理员", "图书馆管理员", "蔷薇魔女", "丽莎阿姨", "丽莎·敏兹", "敏兹",
        )
    )

    @SerialName("10000014")
    val barbara by value(
        listOf(
            "芭芭拉",
            "Barbara",
            "barbara",
            "巴巴拉",
            "拉粑粑",
            "拉巴巴",
            "内鬼",
            "加湿器",
            "肉身解咒",
            "肉身解咒真君",
            "闪耀偶像",
            "偶像",
            "芭芭拉·佩奇",
            "佩奇",
        )
    )

    @SerialName("10000015")
    val kaeya by value(
        listOf(
            "凯亚",
            "Kaeya",
            "kaeya",
            "盖亚",
            "凯子哥",
            "凯鸭",
            "矿工",
            "矿工头子",
            "骑兵队长",
            "凯子",
            "凝冰渡海真君",
            "凯亚·亚尔伯里奇",
            "亚尔伯里奇",
        )
    )

    @SerialName("10000016")
    val diluc by value(
        listOf(
            "迪卢克",
            "diluc",
            "Diluc",
            "卢姥爷",
            "姥爷",
            "卢老爷",
            "卢锅巴",
            "正义人",
            "正e人",
            "正E人",
            "卢本伟",
            "暗夜英雄",
            "卢卢伯爵",
            "落魄了",
            "落魄了家人们",
            "迪卢克·莱艮芬德",
            "莱艮芬德",
        )
    )

    @SerialName("10000020")
    val razor by value(
        listOf(
            "雷泽", "razor", "Razor", "狼少年", "狼崽子", "狼崽", "卢皮卡", "小狼", "小狼狗", "狼孩",
        )
    )

    @SerialName("10000021")
    val amber by value(
        listOf(
            "安柏",
            "Amber",
            "amber",
            "安伯",
            "兔兔伯爵",
            "飞行冠军",
            "侦查骑士",
            "点火姬",
            "点火机",
            "打火机",
            "打火姬",
        )
    )

    @SerialName("10000022")
    val venti by value(
        listOf(
            "温迪",
            "Venti",
            "venti",
            "温蒂",
            "风神",
            "卖唱的",
            "巴巴托斯",
            "巴巴脱丝",
            "芭芭托斯",
            "芭芭脱丝",
            "干点正事",
            "不干正事",
            "吟游诗人",
            "诶嘿",
            "唉嘿",
            "摸鱼",
        )
    )

    @SerialName("10000023")
    val xiangling by value(
        listOf(
            "香菱", "Xiangling", "xiangling", "香玲", "锅巴", "厨师", "万民堂厨师", "香师傅", "卯香菱",
        )
    )

    @SerialName("10000024")
    val beidou by value(
        listOf(
            "北斗", "Beidou", "beidou", "大姐头", "大姐", "无冕的龙王", "龙王",
        )
    )

    @SerialName("10000025")
    val xingqiu by value(
        listOf(
            "行秋", "Xingqiu", "xingqiu", "秋秋人", "秋妹妹", "书呆子", "水神", "飞云商会二少爷",
        )
    )

    @SerialName("10000026")
    val xiao by value(
        listOf(
            "魈",
            "Xiao",
            "xiao",
            "打桩机",
            "插秧",
            "三眼五显仙人",
            "三眼五显真人",
            "降魔大圣",
            "护法夜叉",
            "快乐风男",
            "无聊",
            "靖妖傩舞",
            "矮子仙人",
            "三点五尺仙人",
            "跳跳虎",
        )
    )

    @SerialName("10000027")
    val ningguang by value(
        listOf(
            "凝光", "Ningguang", "ningguang", "富婆", "天权星", "天权",
        )
    )

    @SerialName("10000029")
    val klee by value(
        listOf(
            "可莉",
            "Klee",
            "klee",
            "嘟嘟可",
            "火花骑士",
            "蹦蹦炸弹",
            "炸鱼",
            "放火烧山",
            "放火烧山真君",
            "蒙德最强战力",
            "逃跑的太阳",
            "啦啦啦",
            "哒哒哒",
            "炸弹人",
            "禁闭室",
            "太阳",
            "小太阳",
        )
    )

    @SerialName("10000030")
    val zhongli by value(
        listOf(
            "钟离",
            "Zhongli",
            "zhongli",
            "摩拉克斯",
            "岩王爷",
            "岩神",
            "钟师傅",
            "天动万象",
            "岩王帝君",
            "未来可期",
            "帝君",
            "拒收病婿",
        )
    )

    @SerialName("10000031")
    val fischl by value(
        listOf(
            "菲谢尔",
            "Fischl",
            "fischl",
            "皇女",
            "小艾米",
            "小艾咪",
            "奥兹",
            "断罪皇女",
            "中二病",
            "中二少女",
            "中二皇女",
            "奥兹发射器",
            "菲谢尔·冯·露弗施洛斯·那菲多特",
            "露弗施洛斯",
            "那菲多特",
        )
    )

    @SerialName("10000032")
    val bennett by value(
        listOf(
            "班尼特",
            "Bennett",
            "bennett",
            "点赞哥",
            "点赞",
            "倒霉少年",
            "倒霉蛋",
            "霹雳闪雷真君",
            "班神",
            "班爷",
            "倒霉",
            "火神",
            "六星真神",
        )
    )

    @SerialName("10000033")
    val tartaglia by value(
        listOf(

            "达达利亚",
            "Tartaglia",
            "tartaglia",
            "Childe",
            "childe",
            "Ajax",
            "ajax",
            "达达鸭",
            "达达利鸭",
            "公子",
            "玩具销售员",
            "玩具推销员",
            "钱包",
            "鸭鸭",
            "愚人众末席",
            "阿贾克斯",
        )
    )

    @SerialName("10000034")
    val noelle by value(
        listOf(
            "诺艾尔", "Noelle", "noelle", "女仆", "高达", "岩王帝姬",
        )
    )

    @SerialName("10000035")
    val qiqi by value(
        listOf(
            "七七", "Qiqi", "qiqi", "僵尸", "肚饿真君", "度厄真君", "77",
        )
    )

    @SerialName("10000036")
    val chongyun by value(
        listOf(
            "重云", "Chongyun", "chongyun", "纯阳之体", "冰棍",
        )
    )

    @SerialName("10000037")
    val ganyu by value(
        listOf(
            "甘雨", "Ganyu", "ganyu", "椰羊", "椰奶", "王小美",
        )
    )

    @SerialName("10000038")
    val albedo by value(
        listOf(
            "阿贝多",
            "Albedo",
            "albedo",
            "可莉哥哥",
            "升降机",
            "升降台",
            "电梯",
            "白垩之子",
            "贝爷",
            "白垩",
            "阿贝少",
            "花呗多",
            "阿贝夕",
            "abd",
            "阿师傅",
        )
    )

    @SerialName("10000039")
    val diona by value(
        listOf(
            "迪奥娜",
            "Diona",
            "diona",
            "迪欧娜",
            "dio",
            "dio娜",
            "冰猫",
            "猫猫",
            "猫娘",
            "喵喵",
            "调酒师",
            "迪奥娜·凯茨莱茵",
            "凯茨莱茵",
        )
    )

    @SerialName("10000041")
    val mona by value(
        listOf(
            "莫娜",
            "Mona",
            "mona",
            "穷鬼",
            "穷光蛋",
            "穷",
            "莫纳",
            "占星术士",
            "占星师",
            "讨龙真君",
            "半部讨龙真君",
            "阿斯托洛吉斯·莫娜·梅姬斯图斯",
            "阿斯托洛吉斯",
            "梅姬斯图斯",
            "梅姬斯图斯姬",
            "梅姬斯图斯卿",
        )
    )

    @SerialName("10000042")
    val keqing by value(
        listOf(
            "刻晴",
            "Keqing",
            "keqing",
            "刻情",
            "氪晴",
            "刻师傅",
            "刻师父",
            "牛杂",
            "牛杂师傅",
            "斩尽牛杂",
            "免疫",
            "免疫免疫",
            "屁斜剑法",
            "玉衡星",
            "玉衡",
            "阿晴",
            "啊晴",
            "璃月雷神",
        )
    )

    @SerialName("10000043")
    val sucrose by value(
        listOf(
            "砂糖", "Sucrose", "sucrose", "雷莹术士", "雷萤术士", "雷荧术士",
        )
    )

    @SerialName("10000044")
    val xinyan by value(
        listOf(
            "辛焱", "Xinyan", "xinyan", "辛炎", "黑妹", "摇滚",
        )
    )

    @SerialName("10000045")
    val rosaria by value(
        listOf(
            "罗莎莉亚",
            "Rosaria",
            "rosaria",
            "罗莎莉娅",
            "白色史莱姆",
            "白史莱姆",
            "修女",
            "罗莎利亚",
            "罗莎利娅",
            "罗沙莉亚",
            "罗沙莉娅",
            "罗沙利亚",
            "罗沙利娅",
            "萝莎莉亚",
            "萝莎莉娅",
            "萝莎利亚",
            "萝莎利娅",
            "萝沙莉亚",
            "萝沙莉娅",
            "萝沙利亚",
            "萝沙利娅",
        )
    )

    @SerialName("10000046")
    val hutao by value(
        listOf(
            "胡桃",
            "Hu Tao",
            "hu tao",
            "HuTao",
            "hutao",
            "Hutao",
            "胡淘",
            "往生堂堂主",
            "火化",
            "抬棺的",
            "蝴蝶",
            "核桃",
            "堂主",
            "胡堂主",
            "雪霁梅香",
            "桃子",
        )
    )

    @SerialName("10000047")
    val kazuha by value(
        listOf(
            "枫原万叶", "Kaedehara Kazuha", "Kazuha", "kazuha", "万叶", "叶天帝", "天帝", "叶师傅",
        )
    )

    @SerialName("10000048")
    val yanfei by value(
        listOf(
            "烟绯", "Yanfei", "yanfei", "烟老师", "律师", "罗翔",
        )
    )

    @SerialName("10000049")
    val yoimiya by value(
        listOf(
            "宵宫", "Yoimiya", "yoimiya", "霄宫", "烟花", "肖宫", "肖工", "绷带女孩", "长野原宵宫",
        )
    )

    @SerialName("10000050")
    val thoma by value(
        listOf(
            "托马", "Thoma", "thoma", "家政官", "太郎丸", "地头蛇", "男仆", "拖马",
        )
    )

    @SerialName("10000051")
    val eula by value(
        listOf(
            "优菈", "Eula", "eula", "优拉", "尤拉", "尤菈", "浪花骑士", "记仇", "优菈·劳伦斯", "劳伦斯",
        )
    )

    @SerialName("10000052")
    val raiden by value(
        listOf(
            "雷电将军",
            "Raiden Shogun",
            "Raiden",
            "raiden",
            "雷神",
            "将军",
            "雷军",
            "巴尔",
            "阿影",
            "影",
            "巴尔泽布",
            "煮饭婆",
            "奶香一刀",
            "无想一刀",
            "宅女",
        )
    )

    @SerialName("10000053")
    val sayu by value(
        listOf(
            "早柚", "Sayu", "sayu", "小狸猫", "狸猫", "忍者", "貉",
        )
    )

    @SerialName("10000054")
    val kokomi by value(
        listOf(
            "珊瑚宫心海",
            "Sangonomiya Kokomi",
            "Kokomi",
            "kokomi",
            "心海",
            "军师",
            "珊瑚宫",
            "书记",
            "观赏鱼",
            "水母",
            "鱼",
            "美人鱼",
        )
    )

    @SerialName("10000055")
    val gorou by value(
        listOf(
            "五郎", "Gorou", "gorou", "柴犬", "土狗", "希娜", "希娜小姐",
        )
    )

    @SerialName("10000056")
    val sara by value(
        listOf(
            "九条裟罗", "Kujou Sara", "Sara", "sara", "九条", "九条沙罗", "裟罗", "沙罗", "天狗",
        )
    )

    @SerialName("10000057")
    val itto by value(
        listOf(
            "荒泷一斗",
            "Arataki Itto",
            "Itto",
            "itto",
            "荒龙一斗",
            "荒泷天下第一斗",
            "一斗",
            "一抖",
            "荒泷",
            "1斗",
            "牛牛",
            "斗子哥",
            "牛子哥",
            "牛子",
            "孩子王",
            "斗虫",
            "巧乐兹",
            "放牛的",
        )
    )

    @SerialName("10000058")
    val miko by value(
        listOf(
            "八重神子",
            "Yae Miko",
            "Miko",
            "miko",
            "八重",
            "神子",
            "狐狸",
            "想得美哦",
            "巫女",
            "屑狐狸",
            "骚狐狸",
            "八重宫司",
            "婶子",
            "小八",
            "八重寄子",
            "寄子",
        )
    )

    @SerialName("10000059")
    val heizou by value(
        listOf(
            "鹿野院平藏", "shikanoin heizou", "Heizou", "heizou", "鹿野苑", "鹿野院", "平藏", "鹿野苑平藏", "小鹿",
        )
    )

    @SerialName("10000060")
    val yelan by value(
        listOf(
            "夜兰", "Yelan", "yelan", "夜阑", "叶澜", "腋兰", "夜天后",
        )
    )

    @SerialName("10000062")
    val aloy by value(
        listOf(
            "埃洛伊", "Aloy", "aloy",
        )
    )

    @SerialName("10000063")
    val shenhe by value(
        listOf(
            "申鹤", "Shenhe", "shenhe", "神鹤", "小姨", "小姨子", "审鹤",
        )
    )

    @SerialName("10000064")
    val yunjin by value(
        listOf(
            "云堇", "Yun Jin", "yunjin", "yun jin", "云瑾", "云先生", "云锦", "神女劈观", "土女",
        )
    )

    @SerialName("10000065")
    val kuki by value(
        listOf(
            "久岐忍",
            "Kuki Shinobu",
            "Kuki",
            "kuki",
            "Shinobu",
            "shinobu",
            "97忍",
            "小忍",
            "久歧忍",
            "97",
            "茄忍",
            "茄子",
            "紫茄子",
            "阿忍",
            "忍姐",
        )
    )

    @SerialName("10000066")
    val ayato by value(
        listOf(
            "神里绫人", "Kamisato Ayato", "Ayato", "ayato", "绫人", "神里凌人", "凌人", "0人", "神人", "零人", "大舅哥",
        )
    )

    @SerialName("10000067")
    val collei by value(
        listOf(
            "柯莱",
            "Collei",
            "collei",
            "柯来",
            "科莱",
            "科来",
            "小天使",
            "须弥安柏",
            "草安柏",
            "须弥飞行冠军",
            "见习巡林员",
            "巡林员",
        )
    )

    @SerialName("10000068")
    val dori by value(
        listOf(
            "多莉", "Dori", "dori", "多利", "多力", "多丽", "奸商", "多莉·桑歌玛哈巴依", "桑歌玛哈巴依",
        )
    )

    @SerialName("10000069")
    val tighnari by value(
        listOf(
            "提纳里",
            "Tighnari",
            "tighnari",
            "小提",
            "提那里",
            "缇娜里",
            "提哪里",
            "驴",
            "柯莱老师",
            "柯莱师傅",
            "柯莱师父",
            "巡林官",
        )
    )

    @SerialName("10000070")
    val nilou by value(
        listOf(
            "妮露", "Nilou", "nilou", "尼露", "尼禄", "妮璐", "舞娘", "红牛",
        )
    )

    @SerialName("10000071")
    val cyno by value(
        listOf(
            "赛诺", "Cyno", "cyno", "塞诺", "胡狼", "牌佬", "打牌佬", "打牌王",
        )
    )

    @SerialName("10000072")
    val candace by value(
        listOf(
            "坎蒂丝", "Candace", "candace", "坎迪斯",
        )
    )

    @SerialName("10000073")
    val nahida by value(
        listOf(
            "纳西妲",
            "Nahida",
            "nahida",
            "草王",
            "草神",
            "花神",
            "小吉祥",
            "小吉祥草王",
            "大慈树王",
            "草萝莉",
            "纳西坦",
            "羽毛球",
        )
    )

    @SerialName("10000074")
    val layla by value(
        listOf(
            "莱依拉", "Layla", "layla", "拉一拉", "莱依菈", "来依菈", "来依拉",
        )
    )

    @SerialName("10000075")
    val wanderer by value(
        listOf(
            "流浪者", "The Wanderer", "Wanderer", "wanderer", "散兵", "伞兵",
        )
    )

    @SerialName("10000076")
    val faruzan by value(
        listOf(
            "珐露珊", "Faruzan", "faruzan", "法露珊", "发露珊", "法露姗", "发露姗", "发姐", "法姐", "百岁珊",
        )
    )

    @SerialName("10000077")
    val yaoyao by value(
        listOf(
            "瑶瑶", "Yaoyao", "yaoyao", "遥遥",
        )
    )

    @SerialName("10000078")
    val alhaitham by value(
        listOf(
            "艾尔海森", "Alhaitham", "alhaitham", "爱尔海森", "艾尔海参", "艾尔", "海森", "海参", "海神",
        )
    )

    @SerialName("10000079")
    val dehya by value(
        listOf(
            "迪希雅", "Dehya", "dehya", "蒂西亚", "迪希亚", "西亚", "西雅", "希雅", "鬃狮", "希望工程",
        )
    )

    @SerialName("10000080")
    val mika by value(
        listOf(
            "米卡", "Mika", "mika", "米咖",
        )
    )
}