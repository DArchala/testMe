package pl.archala.testme.dto;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class DataTableSortPage {

    private int page;
    private int length;

    @NotEmpty
    private String column;

    @NotEmpty
    private String direction;

    public DataTableSortPage() {
    }

    public DataTableSortPage(int page, int length, String column, String direction) {
        this.page = page;
        this.length = length;
        this.column = column;
        this.direction = direction;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "DataTableSortPage{" +
                "page=" + page +
                ", length=" + length +
                ", column='" + column + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataTableSortPage that = (DataTableSortPage) o;
        return page == that.page && length == that.length && Objects.equals(column, that.column) && Objects.equals(direction, that.direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, length, column, direction);
    }
}
