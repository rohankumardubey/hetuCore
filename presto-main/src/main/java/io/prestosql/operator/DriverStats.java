/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.prestosql.operator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.airlift.units.DataSize;
import io.airlift.units.Duration;
import io.prestosql.execution.Lifespan;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import java.util.List;
import java.util.Set;

import static io.airlift.units.DataSize.Unit.BYTE;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Immutable
public class DriverStats
{
    private final Lifespan lifespan;

    private final DateTime createTime;
    private final DateTime startTime;
    private final DateTime endTime;

    private final Duration queuedTime;
    private final Duration elapsedTime;

    private final DataSize userMemoryReservation;
    private final DataSize revocableMemoryReservation;
    private final DataSize systemMemoryReservation;

    private final Duration totalScheduledTime;
    private final Duration totalCpuTime;
    private final Duration inputBlockedTime;
    private final Duration outputBlockedTime;
    private final Duration totalBlockedTime;
    private final boolean fullyBlocked;
    private final Set<BlockedReason> blockedReasons;

    private final DataSize physicalInputDataSize;
    private final long physicalInputPositions;
    private final Duration physicalInputReadTime;

    private final DataSize internalNetworkInputDataSize;
    private final long internalNetworkInputPositions;
    private final Duration internalNetworkInputReadTime;

    private final DataSize rawInputDataSize;
    private final long rawInputPositions;
    private final Duration rawInputReadTime;

    private final DataSize processedInputDataSize;
    private final long processedInputPositions;

    private final DataSize outputDataSize;
    private final long outputPositions;

    private final DataSize physicalWrittenDataSize;

    private final List<OperatorStats> operatorStats;

    public DriverStats()
    {
        this.lifespan = null;

        this.createTime = DateTime.now();
        this.startTime = null;
        this.endTime = null;
        this.queuedTime = new Duration(0, MILLISECONDS);
        this.elapsedTime = new Duration(0, MILLISECONDS);

        this.userMemoryReservation = new DataSize(0, BYTE);
        this.revocableMemoryReservation = new DataSize(0, BYTE);
        this.systemMemoryReservation = new DataSize(0, BYTE);

        this.totalScheduledTime = new Duration(0, MILLISECONDS);
        this.totalCpuTime = new Duration(0, MILLISECONDS);
        this.totalBlockedTime = new Duration(0, MILLISECONDS);
        this.fullyBlocked = false;
        this.blockedReasons = ImmutableSet.of();

        this.physicalInputDataSize = new DataSize(0, BYTE);
        this.physicalInputPositions = 0;
        this.physicalInputReadTime = new Duration(0, MILLISECONDS);

        this.internalNetworkInputDataSize = new DataSize(0, BYTE);
        this.internalNetworkInputPositions = 0;
        this.internalNetworkInputReadTime = new Duration(0, MILLISECONDS);

        this.rawInputDataSize = new DataSize(0, BYTE);
        this.rawInputPositions = 0;
        this.rawInputReadTime = new Duration(0, MILLISECONDS);

        this.processedInputDataSize = new DataSize(0, BYTE);
        this.processedInputPositions = 0;

        this.outputDataSize = new DataSize(0, BYTE);
        this.outputPositions = 0;

        this.physicalWrittenDataSize = new DataSize(0, BYTE);

        this.operatorStats = ImmutableList.of();
        this.inputBlockedTime = new Duration(0, MILLISECONDS);
        this.outputBlockedTime = new Duration(0, MILLISECONDS);
    }

    @JsonCreator
    public DriverStats(
            @JsonProperty("lifespan") Lifespan lifespan,

            @JsonProperty("createTime") DateTime createTime,
            @JsonProperty("startTime") DateTime startTime,
            @JsonProperty("endTime") DateTime endTime,
            @JsonProperty("queuedTime") Duration queuedTime,
            @JsonProperty("elapsedTime") Duration elapsedTime,

            @JsonProperty("userMemoryReservation") DataSize userMemoryReservation,
            @JsonProperty("revocableMemoryReservation") DataSize revocableMemoryReservation,
            @JsonProperty("systemMemoryReservation") DataSize systemMemoryReservation,

            @JsonProperty("totalScheduledTime") Duration totalScheduledTime,
            @JsonProperty("totalCpuTime") Duration totalCpuTime,
            @JsonProperty("totalBlockedTime") Duration totalBlockedTime,
            @JsonProperty("fullyBlocked") boolean fullyBlocked,
            @JsonProperty("blockedReasons") Set<BlockedReason> blockedReasons,

            @JsonProperty("physicalInputDataSize") DataSize physicalInputDataSize,
            @JsonProperty("physicalInputPositions") long physicalInputPositions,
            @JsonProperty("physicalInputReadTime") Duration physicalInputReadTime,

            @JsonProperty("internalNetworkInputDataSize") DataSize internalNetworkInputDataSize,
            @JsonProperty("internalNetworkInputPositions") long internalNetworkInputPositions,
            @JsonProperty("internalNetworkInputReadTime") Duration internalNetworkInputReadTime,

            @JsonProperty("rawInputDataSize") DataSize rawInputDataSize,
            @JsonProperty("rawInputPositions") long rawInputPositions,
            @JsonProperty("rawInputReadTime") Duration rawInputReadTime,

            @JsonProperty("processedInputDataSize") DataSize processedInputDataSize,
            @JsonProperty("processedInputPositions") long processedInputPositions,

            @JsonProperty("outputDataSize") DataSize outputDataSize,
            @JsonProperty("outputPositions") long outputPositions,

            @JsonProperty("physicalWrittenDataSize") DataSize physicalWrittenDataSize,

            @JsonProperty("operatorStats") List<OperatorStats> operatorStats,

            @JsonProperty("inputBlockedTime") Duration inputBlockedTime,
            @JsonProperty("outputBlockedTime") Duration outputBlockedTime)
    {
        this.lifespan = lifespan;

        this.createTime = requireNonNull(createTime, "createTime is null");
        this.startTime = startTime;
        this.endTime = endTime;
        this.queuedTime = requireNonNull(queuedTime, "queuedTime is null");
        this.elapsedTime = requireNonNull(elapsedTime, "elapsedTime is null");

        this.userMemoryReservation = requireNonNull(userMemoryReservation, "userMemoryReservation is null");
        this.revocableMemoryReservation = requireNonNull(revocableMemoryReservation, "revocableMemoryReservation is null");
        this.systemMemoryReservation = requireNonNull(systemMemoryReservation, "systemMemoryReservation is null");

        this.totalScheduledTime = requireNonNull(totalScheduledTime, "totalScheduledTime is null");
        this.totalCpuTime = requireNonNull(totalCpuTime, "totalCpuTime is null");
        this.totalBlockedTime = requireNonNull(totalBlockedTime, "totalBlockedTime is null");
        this.fullyBlocked = fullyBlocked;
        this.blockedReasons = ImmutableSet.copyOf(requireNonNull(blockedReasons, "blockedReasons is null"));

        this.physicalInputDataSize = requireNonNull(physicalInputDataSize, "physicalInputDataSize is null");
        Preconditions.checkArgument(physicalInputPositions >= 0, "physicalInputPositions is negative");
        this.physicalInputPositions = physicalInputPositions;
        this.physicalInputReadTime = requireNonNull(physicalInputReadTime, "physicalInputReadTime is null");

        this.internalNetworkInputDataSize = requireNonNull(internalNetworkInputDataSize, "internalNetworkInputDataSize is null");
        Preconditions.checkArgument(internalNetworkInputPositions >= 0, "internalNetworkInputPositions is negative");
        this.internalNetworkInputPositions = internalNetworkInputPositions;
        this.internalNetworkInputReadTime = requireNonNull(internalNetworkInputReadTime, "internalNetworkInputReadTime is null");

        this.rawInputDataSize = requireNonNull(rawInputDataSize, "rawInputDataSize is null");
        Preconditions.checkArgument(rawInputPositions >= 0, "rawInputPositions is negative");
        this.rawInputPositions = rawInputPositions;
        this.rawInputReadTime = requireNonNull(rawInputReadTime, "rawInputReadTime is null");

        this.processedInputDataSize = requireNonNull(processedInputDataSize, "processedInputDataSize is null");
        Preconditions.checkArgument(processedInputPositions >= 0, "processedInputPositions is negative");
        this.processedInputPositions = processedInputPositions;

        this.outputDataSize = requireNonNull(outputDataSize, "outputDataSize is null");
        Preconditions.checkArgument(outputPositions >= 0, "outputPositions is negative");
        this.outputPositions = outputPositions;

        this.physicalWrittenDataSize = requireNonNull(physicalWrittenDataSize, "writtenDataSize is null");

        this.operatorStats = ImmutableList.copyOf(requireNonNull(operatorStats, "operatorStats is null"));

        this.inputBlockedTime = requireNonNull(inputBlockedTime, "inputBlockedTime is null");
        this.outputBlockedTime = requireNonNull(outputBlockedTime, "outputBlockedTime is null");
    }

    @JsonProperty
    public Lifespan getLifespan()
    {
        return lifespan;
    }

    @JsonProperty
    public DateTime getCreateTime()
    {
        return createTime;
    }

    @Nullable
    @JsonProperty
    public DateTime getStartTime()
    {
        return startTime;
    }

    @Nullable
    @JsonProperty
    public DateTime getEndTime()
    {
        return endTime;
    }

    @JsonProperty
    public Duration getQueuedTime()
    {
        return queuedTime;
    }

    @JsonProperty
    public Duration getElapsedTime()
    {
        return elapsedTime;
    }

    @JsonProperty
    public DataSize getUserMemoryReservation()
    {
        return userMemoryReservation;
    }

    @JsonProperty
    public DataSize getRevocableMemoryReservation()
    {
        return revocableMemoryReservation;
    }

    @JsonProperty
    public DataSize getSystemMemoryReservation()
    {
        return systemMemoryReservation;
    }

    @JsonProperty
    public Duration getTotalScheduledTime()
    {
        return totalScheduledTime;
    }

    @JsonProperty
    public Duration getTotalCpuTime()
    {
        return totalCpuTime;
    }

    @JsonProperty
    public Duration getTotalBlockedTime()
    {
        return totalBlockedTime;
    }

    @JsonProperty
    public boolean isFullyBlocked()
    {
        return fullyBlocked;
    }

    @JsonProperty
    public Set<BlockedReason> getBlockedReasons()
    {
        return blockedReasons;
    }

    @JsonProperty
    public DataSize getPhysicalInputDataSize()
    {
        return physicalInputDataSize;
    }

    @JsonProperty
    public long getPhysicalInputPositions()
    {
        return physicalInputPositions;
    }

    @JsonProperty
    public Duration getPhysicalInputReadTime()
    {
        return physicalInputReadTime;
    }

    @JsonProperty
    public DataSize getInternalNetworkInputDataSize()
    {
        return internalNetworkInputDataSize;
    }

    @JsonProperty
    public long getInternalNetworkInputPositions()
    {
        return internalNetworkInputPositions;
    }

    @JsonProperty
    public Duration getInternalNetworkInputReadTime()
    {
        return internalNetworkInputReadTime;
    }

    @JsonProperty
    public DataSize getRawInputDataSize()
    {
        return rawInputDataSize;
    }

    @JsonProperty
    public long getRawInputPositions()
    {
        return rawInputPositions;
    }

    @JsonProperty
    public Duration getRawInputReadTime()
    {
        return rawInputReadTime;
    }

    @JsonProperty
    public DataSize getProcessedInputDataSize()
    {
        return processedInputDataSize;
    }

    @JsonProperty
    public long getProcessedInputPositions()
    {
        return processedInputPositions;
    }

    @JsonProperty
    public DataSize getOutputDataSize()
    {
        return outputDataSize;
    }

    @JsonProperty
    public long getOutputPositions()
    {
        return outputPositions;
    }

    @JsonProperty
    public DataSize getPhysicalWrittenDataSize()
    {
        return physicalWrittenDataSize;
    }

    @JsonProperty
    public List<OperatorStats> getOperatorStats()
    {
        return operatorStats;
    }

    @JsonProperty
    public Duration getInputBlockedTime()
    {
        return inputBlockedTime;
    }

    @JsonProperty
    public Duration getOutputBlockedTime()
    {
        return outputBlockedTime;
    }
}
