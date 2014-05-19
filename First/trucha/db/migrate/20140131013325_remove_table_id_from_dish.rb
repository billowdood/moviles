class RemoveTableIdFromDish < ActiveRecord::Migration
  def change
    remove_column :dishes, :table_id, :integer
  end
end
