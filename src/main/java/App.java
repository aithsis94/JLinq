import com.jlinq.Linq;
import com.jlinq.tuple.Tuple2;
import com.jlinq.util.Util;

import java.util.ArrayList;
import java.util.List;

import static com.jlinq.Linq.from;

public class App {

    public static void main(String[] args) {

        List<Integer> firstList = new ArrayList<>();
        firstList.add(1);
        firstList.add(1);
        firstList.add(2);
        firstList.add(3);
        firstList.add(4);
        firstList.add(5);
        firstList.add(5);
        firstList.add(5);
        firstList.add(6);
        firstList.add(7);


        List<Integer> secondList = new ArrayList<>();
        secondList.add(11);
        secondList.add(21);
        secondList.add(41);
        secondList.add(5);
        secondList.add(61);
        secondList.add(3);
        secondList.add(81);

        Iterable<Integer> iterable =
                from(firstList)
                        .unique()
                        .where(i -> i % 2 != 0)
                        .join(secondList)
                        .orderBy((i, j) -> i.compareTo(j))
                        .range(3, 4)
                        .last(2);

        for(Integer data : iterable){
            System.out.println(data);
        }
    }
}
