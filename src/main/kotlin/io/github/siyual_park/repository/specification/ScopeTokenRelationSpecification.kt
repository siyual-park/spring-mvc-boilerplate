package io.github.siyual_park.repository.specification

import io.github.siyual_park.model.scope.ScopeToken
import io.github.siyual_park.model.scope.ScopeTokenRelation
import io.github.siyual_park.repository.expansion.get
import org.springframework.data.jpa.domain.Specification

object ScopeTokenRelationSpecification {
    fun withParent(parent: ScopeToken) = withParent(parent.id!!)

    fun withParent(parentId: String) = Specification<ScopeTokenRelation> { root, _, builder ->
        builder.equal(root[ScopeTokenRelation::parent][ScopeToken::id], parentId)
    }
}
