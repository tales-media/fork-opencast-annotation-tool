/**
 *  Copyright 2012, Entwine GmbH, Switzerland
 *  Licensed under the Educational Community License, Version 2.0
 *  (the "License"); you may not use this file except in compliance
 *  with the License. You may obtain a copy of the License at
 *
 *  http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 *
 */
package org.opencast.annotation.impl;

import org.opencastproject.util.EqualsUtil;
import org.opencastproject.util.data.Function;
import org.opencastproject.util.data.Option;

import org.opencast.annotation.api.Category;
import org.opencast.annotation.api.ExtendedAnnotationService;
import org.opencast.annotation.api.Label;
import org.opencast.annotation.api.Resource;

/**
 * The business model implementation of {@link org.opencast.annotation.api.Label}.
 */
public final class LabelImpl extends ResourceImpl implements Label {

  private final long id;
  private final long categoryId;
  private final String value;
  private final String abbreviation;
  private final Option<String> description;
  private final Option<String> settings;

  public LabelImpl(long id, long categoryId, String value, String abbreviation, Option<String> description,
          Option<String> settings, Resource resource) {
    super(Option.option(resource.getAccess()), resource.getCreatedBy(), resource.getUpdatedBy(), resource
            .getDeletedBy(), resource.getCreatedAt(), resource.getUpdatedAt(), resource.getDeletedAt(), resource
            .getTags());
    this.id = id;
    this.categoryId = categoryId;
    this.value = value;
    this.abbreviation = abbreviation;
    this.description = description;
    this.settings = settings;
  }

  /**
   * @see org.opencast.annotation.api.Label#getId()
   */
  @Override
  public long getId() {
    return id;
  }

  /**
   * @see org.opencast.annotation.api.Resource#getVideo(ExtendedAnnotationService)
   */
  @Override
  public Option<Long> getVideo(final ExtendedAnnotationService eas) {
    final boolean includeDeleted = true;
    return eas.getCategory(categoryId, includeDeleted).bind(new Function<Category, Option<Long>>() {
      @Override
      public Option<Long> apply(Category category) {
        return category.getVideo(eas);
      }
    });
  }

  /**
   * @see org.opencast.annotation.api.Label#getCategoryId()
   */
  @Override
  public long getCategoryId() {
    return categoryId;
  }

  /**
   * @see org.opencast.annotation.api.Label#getValue()
   */
  @Override
  public String getValue() {
    return value;
  }

  /**
   * @see org.opencast.annotation.api.Label#getAbbreviation()
   */
  @Override
  public String getAbbreviation() {
    return abbreviation;
  }

  /**
   * @see org.opencast.annotation.api.Label#getDescription()
   */
  @Override
  public Option<String> getDescription() {
    return description;
  }

  /**
   * @see org.opencast.annotation.api.Label#getSettings()
   */
  @Override
  public Option<String> getSettings() {
    return settings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Label label = (Label) o;
    return id == label.getId() && categoryId == label.getCategoryId() && value.equals(label.getValue())
            && abbreviation.equals(label.getAbbreviation()) && description.equals(label.getDescription())
            && settings.equals(label.getSettings()) && getTags().equals(label.getTags());
  }

  @Override
  public int hashCode() {
    return EqualsUtil.hash(id, categoryId, value, abbreviation, description, settings, getTags());
  }

}
