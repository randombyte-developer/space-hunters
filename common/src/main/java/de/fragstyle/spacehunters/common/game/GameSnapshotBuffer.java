package de.fragstyle.spacehunters.common.game;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GameSnapshotBuffer {

  private final Map<Long, GameSnapshot> snapshots = new ConcurrentHashMap<>();

  public GameSnapshotBuffer() {
  }

  public void addState(GameSnapshot gameSnapshot) {
    long snapshotTime = gameSnapshot.getTime();
    snapshots.put(snapshotTime, gameSnapshot);
  }

  public Map<Long, GameSnapshot> getSnapshots() {
    return snapshots;
  }

  public List<Long> getSnapshotTimesAfter(long snapshotTime) {
    return snapshots.keySet()
        .stream()
        .filter(time -> time > snapshotTime)
        .sorted()
        .collect(Collectors.toList());
  }

  public Optional<Long> getNextSnapshotTimeAfter(long snapshotTime) {
    List<Long> nextSnapshotTimes = getSnapshotTimesAfter(snapshotTime);

    if (nextSnapshotTimes.isEmpty()) {
      return Optional.empty();
    }

    long nextSnapshotTime = nextSnapshotTimes.get(0);
    return Optional.of(nextSnapshotTime);
  }

  public Optional<GameSnapshot> getNextSnapshotAfter(long snapshotTime) {
    return getNextSnapshotTimeAfter(snapshotTime).map(snapshots::get);
  }

  public List<GameSnapshot> getSnapshotsAfter(long snapshotTime) {
    return getSnapshotTimesAfter(snapshotTime)
        .stream()
        .map(snapshots::get)
        .collect(Collectors.toList());
  }

  public Optional<Long> getLatestSnapshotTime() {
    List<Long> sortedSnapshotTimes = snapshots.keySet()
        .stream()
        .sorted()
        .collect(Collectors.toList());

    if (sortedSnapshotTimes.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(sortedSnapshotTimes.get(sortedSnapshotTimes.size() - 1));
  }

  public Optional<GameSnapshot> getLatestSnapshotBeforeLimit(long limit) {
    return getLatestSnapshotTimeBeforeLimit(limit).map(snapshots::get);
  }

  public Optional<Long> getLatestSnapshotTimeBeforeLimit(long limit) {
    List<Long> snapshotTimesBeforeLimit = snapshots.keySet()
        .stream()
        .filter(time -> time < limit)
        .sorted()
        .collect(Collectors.toList());

    if (snapshotTimesBeforeLimit.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(snapshotTimesBeforeLimit.get(snapshotTimesBeforeLimit.size() - 1));
  }
}
