package hu.moksony.lazyviewholderexample

class List<T> {
    private var item: T? = null

    fun set(t: T) {
        this.item = t
    }
}