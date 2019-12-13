package ar.edu.itba.paw.webapp.utils;

import ar.edu.itba.paw.webapp.dto.PaginationDTO;

public interface PaginationService {
    PaginationDTO getPaginationDTO(int page, int maxPage);

    int getPageAsOneIfNegative(int page);

    int getPageSizeAsDefaultSizeIfOutOfRange(int pageSize, int defaultSize, int maxSize);

    int maxPage(final int total, final int pageSize);
}
