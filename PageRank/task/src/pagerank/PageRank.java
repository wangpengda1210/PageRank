package pagerank;

public class PageRank implements Comparable<PageRank> {

    private final String websiteName;
    private final double value;

    public PageRank(String websiteName, double value) {
        this.websiteName = websiteName;
        this.value = value;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public double getValue() {
        return value;
    }

    @Override
    public int compareTo(PageRank pageRank) {
        if (this.getValue() == pageRank.getValue()) {
            return pageRank.getWebsiteName().compareTo(this.getWebsiteName());
        } else if (this.getValue() < pageRank.getValue()) {
            return 1;
        } else {
            return -1;
        }
    }

}
