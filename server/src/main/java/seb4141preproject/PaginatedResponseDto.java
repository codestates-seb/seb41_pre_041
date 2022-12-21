package seb4141preproject;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PaginatedResponseDto<T> {
    private final List<T> data;
    private final PageInfo pageInfo;

    @Getter
    @AllArgsConstructor
    public static class PageInfo {
        private final int page;
        private final int size;
        private final long totalElements;
        private final int totalPages;
    }
}
