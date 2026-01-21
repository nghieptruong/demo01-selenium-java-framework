package helpers.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generic data manipulation functions for randomness
 */
public class PickRandomHelper {

    private static final Logger LOG = LogManager.getLogger(PickRandomHelper.class);


    /**
     * Get a random sample of items from the original list.
     * If the requested sample size exceeds the list size, returns the entire list.
     *
     * @param originalList The original list to sample from
     * @param sampleSize   The number of random items to select
     * @param <T>          The type of items in the list
     * @return A list containing the random sample
     */
    public static <T> List <T> getRandomSamplesFromList(List<T> originalList, int sampleSize) {
        int actualSize = Math.min(sampleSize, originalList.size());

        if (actualSize == originalList.size()) {
            LOG.info("Requested sample size ({}) >= available items ({}). Returning all available items.", sampleSize, originalList.size());
            return new ArrayList<>(originalList);
        }

        Random random = new Random();
        List<T> sample = new ArrayList<>();
        List<T> pool = new ArrayList<>(originalList);

        for (int i = 0; i < actualSize; i++) {
            int randomIndex = random.nextInt(pool.size());
            sample.add(pool.remove(randomIndex));
        }

        LOG.info("Selected {} random item from {} available: {}", actualSize, originalList.size(), sample);
        return sample;
    }


    /**
     * Get a single random item from the original list.
     *
     * @param originalList The original list to sample from
     * @param <T>          The type of items in the list
     * @return A single random item
     */
    public static <T> T getRandomSampleFromList(List<T> originalList) {
       return getRandomSamplesFromList(originalList, 1).get(0);
    }

    /**
     * Get a random integer within the specified inclusive range [min, max].
     *
     * @param min Minimum value (inclusive)
     * @param max Maximum value (inclusive)
     * @return Random integer within the range
     */
    public static Integer getRandomIntInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}