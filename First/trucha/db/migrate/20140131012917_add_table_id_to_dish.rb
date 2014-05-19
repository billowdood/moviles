class AddTableIdToDish < ActiveRecord::Migration
  def change
    add_column :dishes, :table_id, :integer
  end
end
