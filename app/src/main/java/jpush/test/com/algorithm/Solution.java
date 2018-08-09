package jpush.test.com.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by jesgoo on 2018/7/19.
 */

public class Solution {

    public List<List<Integer>> threeSum(int[] nums) {
        List<Integer> list = new ArrayList<>();
        HashSet<List<Integer>> list2 = new HashSet<>();
        List<List<Integer>> list3 = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            Integer integerI = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                Integer integerJ = list.get(j);
                for (int z = j + 1; z < list.size(); z++) {
                    Integer integerZ = list.get(z);
                    if (integerI + integerJ + integerZ == 0) {
                        List<Integer> list1 = new ArrayList<>();
                        list1.add(integerI);
                        list1.add(integerJ);
                        list1.add(integerZ);
                        Collections.sort(list1);
                        list2.add(list1);
//                        boolean repeat = false;
//                        for (List<Integer> integers : list2) {
//                            if (integers.get(0) == list1.get(0) && integers.get(1) == list1.get(1) && integers.get(2) == list1.get(2)) {
//                                repeat = true;
//                                break;
//                            }
//
//                        }
//                        if (!repeat) list2.add(list1);
                    }
                }
            }
        }
        for (List<Integer> integers : list2) {
            list3.add(integers);
        }

        return list3;
    }

    public static void main(String[] args) {

        Short wd = Short.valueOf((short) -32512);
        Short sid = (short) 0x8100;

        if (wd == sid) {
            System.out.print("hahhahaa");
        }else {
            System.out.print("不相等");
        }

    }
}
