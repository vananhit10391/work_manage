package vananh.work;

import org.assertj.core.api.AbstractAssert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

/**
 * PageableAssert
 */
class PageableAssert extends AbstractAssert<PageableAssert, Pageable> {

    /**
     * PageableAssert constructor
     *
     * @param pageable
     */
    PageableAssert(Pageable pageable) {
        super(pageable, PageableAssert.class);
    }

    /**
     * assertThat
     *
     * @param actual
     * @return PageableAssert
     */
    static PageableAssert assertThat(Pageable actual) {
        return new PageableAssert(actual);
    }

    /**
     * hasPageSize
     *
     * @param expectedPageSize
     * @return PageableAssert
     */
    PageableAssert hasPageSize(int expectedPageSize) {
        if (!Objects.equals(actual.getPageSize(), expectedPageSize)) {
            failWithMessage("expected page size to be <%s> but was <%s>", expectedPageSize, actual.getPageSize());
        }
        return this;
    }

    /**
     * hasPageNumber
     *
     * @param expectedPageNumber
     * @return PageableAssert
     */
    PageableAssert hasPageNumber(int expectedPageNumber) {
        if (!Objects.equals(actual.getPageNumber(), expectedPageNumber)) {
            failWithMessage("expected page number to be <%s> but was <%s>", expectedPageNumber, actual.getPageNumber());
        }
        return this;
    }

    /**
     * hasSort
     *
     * @param field
     * @param direction
     * @return PageableAssert
     */
    PageableAssert hasSort(String field, Sort.Direction direction) {
        Sort.Order actualOrder = actual.getSort().getOrderFor(field);
        if (actualOrder == null) {
            failWithMessage("expected sort for field <%s> to be <%s> but was null",field, direction);
        } else if (actualOrder.getDirection() != direction) {
            failWithMessage("expected sort for field <%s> to be <%s> but was <%s>", field, direction, actualOrder.getDirection());
        }
        return this;
    }
}
