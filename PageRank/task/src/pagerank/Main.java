package pagerank;

import Jama.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner scanner = new Scanner(System.in);

        int size = scanner.nextInt();
        scanner.nextLine();
        String[] names = scanner.nextLine().split(" ");

        double[][] L = new double[size][size];

        for (int i = 0; i < size; i++) {
            double[] row = new double[size];
            for (int j = 0; j < size; j++) {
                row[j] = scanner.nextDouble();
            }
            L[i] = row;
        }

        String websiteToSearch = scanner.next();

        Matrix m = new Matrix(L);
        double[] pageRank = getPageRank(m, 0.5, 0.1)
                .transpose().getArray()[0];

        PageRank[] ranks = new PageRank[size];
        for (int i = 0; i < size; i++) {
            ranks[i] = new PageRank(names[i], pageRank[i]);
        }

        ArrayList<String> searchResult = new ArrayList<>();
        if (Arrays.stream(ranks).anyMatch(rank -> rank.getWebsiteName().equals(websiteToSearch))) {
            searchResult.add(websiteToSearch);
        }

        for (PageRank rank : ranks) {
            if (rank.getWebsiteName().contains(websiteToSearch) &&
                    !rank.getWebsiteName().equals(websiteToSearch)) {
                searchResult.add(rank.getWebsiteName());
                if (searchResult.size() >= 5) {
                    break;
                }
            }
        }

        Arrays.sort(ranks);
        int i = 0;
        while (i < ranks.length && searchResult.size() < 5) {
            if (!ranks[i].getWebsiteName().equals(websiteToSearch)) {
                searchResult.add(ranks[i].getWebsiteName());
            }
            i++;
        }

        searchResult.forEach(System.out::println);
    }

    public static Matrix getPageRank(Matrix m_undamped, double dampingParameter,
                                     double precision) {
        Matrix j = new Matrix(m_undamped.getRowDimension(),
                m_undamped.getColumnDimension(), 1);
        Matrix m = m_undamped.times(dampingParameter)
                .plus(j.times((1 - dampingParameter) /
                        m_undamped.getRowDimension()));

        double[] r = new double[m.getRowDimension()];
        for (int i = 0; i < r.length; i++) {
            r[i] = 1.00;
            r[i] = 100 * r[i] / r.length;
        }

        Matrix r0 = new Matrix(r, 1).transpose();
        Matrix r0_prev = new Matrix(r.length, 1, 0);

        while (r0.minus(r0_prev).normF() > precision) {
            r0_prev = r0;
            r0 = m.times(r0);
        }

        return r0;
    }
}
