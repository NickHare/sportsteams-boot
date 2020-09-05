package nos.sportsteamsboot.specifications;

import org.springframework.data.jpa.domain.Specification;

import nos.sportsteamsboot.model.Roster;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class RosterSpecifications {
    public static Specification<Roster> isAlex(){
        return (Root<Roster> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder.equal(root.get("id"), 1);
    }

    public static Specification<Roster> isDan(){
        return (Root<Roster> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder.equal(root.get("id"), 2);
    }

    public static Specification<Roster> isBrother(){
        return isAlex().or(isDan());
    }
}
