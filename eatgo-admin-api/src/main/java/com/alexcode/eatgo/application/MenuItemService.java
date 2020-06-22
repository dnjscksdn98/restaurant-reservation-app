package com.alexcode.eatgo.application;

import com.alexcode.eatgo.domain.models.MenuItem;
import com.alexcode.eatgo.domain.MenuItemRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {

  MenuItemRepository menuItemRepository;

  @Autowired
  public MenuItemService(MenuItemRepository menuItemRepository) {
    this.menuItemRepository = menuItemRepository;
  }

  public List<MenuItem> getMenuItems(Long restaurantId) {
    return menuItemRepository.findAllByRestaurantId(restaurantId);
  }

  public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
    for(MenuItem menuItem : menuItems) {
      update(restaurantId, menuItem);
    }
  }

  private void update(Long restaurantId, MenuItem menuItem) {
    if(menuItem.isDestroy()) {
      menuItemRepository.deleteById(menuItem.getId());
      return;
    }
    menuItem.setRestaurantId(restaurantId);
    menuItemRepository.save(menuItem);
  }
}
