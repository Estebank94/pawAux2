package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.webapp.dto.PaginationDTO;
import org.springframework.stereotype.Component;

@Component
public class PaginationHelper implements PaginationService{
    @Override
    public PaginationDTO getPaginationDTO(int page, int maxPage) {
        PaginationDTO links = new PaginationDTO();

        links.setFirst(0);
        links.setLast(maxPage);
        if (page > 0){
            links.setPrev(page - 1);
        }
        if (page < maxPage){
            links.setNext(page + 1);
        }
        return links;
    }

    @Override
    public int getPageAsOneIfNegative(int page) {
        return (page < 0)? 0 : page;
    }

    @Override
    public int getPageSizeAsDefaultSizeIfOutOfRange(int pageSize, int defaultSize, int maxSize) {
        return (pageSize < 0 || pageSize > maxSize)? defaultSize : pageSize;
    }

    @Override
    public int maxPage(int total, int pageSize) {
        return (int)Math.ceil((float) total / pageSize);
    }
}
