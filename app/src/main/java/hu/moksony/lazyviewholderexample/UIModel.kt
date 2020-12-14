package hu.moksony.lazyviewholderexample

sealed class UIModel {
    class HeaderItem(val headerItem: Header): UIModel()
    class BodyItem(val headerItem: Body): UIModel()
}