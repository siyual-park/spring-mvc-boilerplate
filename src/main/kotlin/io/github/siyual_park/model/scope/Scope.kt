package io.github.siyual_park.model.scope

typealias Scope = Set<ScopeToken>

fun Scope.has(scopeTokenName: String): Boolean {
    return this.find { it.name == scopeTokenName } != null
}

fun Scope.normalize(): String {
    return this.joinToString(" ") { it.name }
}
