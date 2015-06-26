package au.aurin.org.io;

import java.util.List;

import org.geotools.data.simple.SimpleFeatureSource;

/**
 * 
 * @author Gerson Galang
 */
public class NonGeospatialDatasetImpl implements Dataset {

  private final GeospatialDataset geoDataset;

  private DataSource dataSource;

  /**
   * Construct a NonGeospatialDatasetImpl object.
   * <p>
   * The constructor for this class was given a friendly access to hide how it
   * is implemented and to force users to only use the DataSourceFactory to get
   * instances of the Dataset implementations.
   * </p>
   * 
   * @param featureSource
   */
  NonGeospatialDatasetImpl(final DataSource dataSource,
      final SimpleFeatureSource featureSource) {
    geoDataset = new GeospatialDatasetImpl(dataSource, featureSource);
  }

  @Override
  public String getName() {
    return geoDataset.getName();
  }

  @Override
  public List<DatasetAttribute> getAttributes() {
    return geoDataset.getAttributes();
  }

  @Override
  public DataCollection getData(final DatasetQueryParams params)
      throws InvalidDataQueryParamsException, DatasetAccessException {
    return geoDataset.getData(params);
  }

  @Override
  public DataSource getDataSource() {
    return geoDataset.getDataSource();
  }

}
