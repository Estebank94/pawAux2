package ar.edu.itba.paw.webapp.utils;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import java.util.stream.Stream;

public class LinkPagination {
    public static final String PREV_REL = "prev";
    public static final String NEXT_REL = "next";
    public static final String FIRST_REL = "first";
    public static final String LAST_REL = "last";
    public static final String PAGE_QUERY_PARAM = "page";
    public static final int FIRST_PAGE = 1;

    private int pageCount;
    private int currentPageIndex;

    public LinkPagination(int currentPageIndex, int pageCount){
        this.pageCount = pageCount;
        this.currentPageIndex = currentPageIndex;
    }

    public Stream<Link> toLinks(UriInfo uriInfo){
        if (currentPageIndex == 1 && pageCount == 1){
            return Stream.empty();
        }
        Stream.Builder<Link> linkStreamBuilder = Stream.builder();

        if (currentPageIndex > FIRST_PAGE){
            linkStreamBuilder.accept(
                    Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                                .replaceQueryParam(PAGE_QUERY_PARAM,
                                                        currentPageIndex - 1))
                        .rel(PREV_REL)
                            .build());
        }

        if (currentPageIndex < pageCount - 1) {
            linkStreamBuilder.accept(
                    Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                                .replaceQueryParam(PAGE_QUERY_PARAM,
                                                        currentPageIndex + 1))
                    .rel(NEXT_REL)
                    .build());
        }
        linkStreamBuilder.accept(
                Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                            .replaceQueryParam(PAGE_QUERY_PARAM,
                                                                FIRST_PAGE))
                .rel(FIRST_REL)
                .build());

        linkStreamBuilder.accept(
                Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                            .replaceQueryParam(PAGE_QUERY_PARAM,
                                                                pageCount))
                .rel(LAST_REL)
                .build());

        return linkStreamBuilder.build();
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }
}
