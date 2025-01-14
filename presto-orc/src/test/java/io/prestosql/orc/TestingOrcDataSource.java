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
package io.prestosql.orc;

import com.google.common.collect.ImmutableList;
import io.airlift.slice.Slice;
import io.prestosql.orc.stream.OrcDataReader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

class TestingOrcDataSource
        implements OrcDataSource
{
    private final OrcDataSource delegate;

    private int readCount;
    private List<DiskRange> lastReadRanges;

    public TestingOrcDataSource(OrcDataSource delegate)
    {
        this.delegate = requireNonNull(delegate, "delegate is null");
    }

    @Override
    public long getEstimatedSize()
    {
        return 0;
    }

    @Override
    public OrcDataSourceId getId()
    {
        return delegate.getId();
    }

    @Override
    public long getLastModifiedTime()
    {
        return delegate.getLastModifiedTime();
    }

    public int getReadCount()
    {
        return readCount;
    }

    public List<DiskRange> getLastReadRanges()
    {
        return lastReadRanges;
    }

    @Override
    public long getReadBytes()
    {
        return delegate.getReadBytes();
    }

    @Override
    public long getReadTimeNanos()
    {
        return delegate.getReadTimeNanos();
    }

    @Override
    public long getSize()
    {
        return delegate.getSize();
    }

    @Override
    public Slice readTail(int length) throws IOException
    {
        return null;
    }

    @Override
    public Slice readFully(long position, int length)
            throws IOException
    {
        readCount++;
        lastReadRanges = ImmutableList.of(new DiskRange(position, length));
        return delegate.readFully(position, length);
    }

    @Override
    public <K> Map<K, OrcDataReader> readFully(Map<K, DiskRange> diskRanges)
            throws IOException
    {
        readCount += diskRanges.size();
        lastReadRanges = ImmutableList.copyOf(diskRanges.values());
        return delegate.readFully(diskRanges);
    }

    @Override
    public long getRetainedSize()
    {
        return 0;
    }
}
