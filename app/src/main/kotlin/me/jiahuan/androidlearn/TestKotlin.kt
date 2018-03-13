package me.jiahuan.androidlearn


fun sum(a: Int, b: Int) : Int {
    return a + b
}

fun sub(a:Int, b:Int) = a - b



fun parseInt(str:String): Int? {
    return null
}


fun use() {
    val x = parseInt("1x")
    val y = parseInt("xx")

    if(x!= null && y != null) {
        x * y
    }
}
